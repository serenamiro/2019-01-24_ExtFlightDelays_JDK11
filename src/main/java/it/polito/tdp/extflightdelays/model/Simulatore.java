package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Simulatore {
	
	// MODELLO
	private Graph<String, DefaultWeightedEdge> grafo;
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Evento> queue;
	
	// PARAMETRI DI SIMULAZIONE
	private int T; // numero di turisti
	private int G; // numero di giorni
	private String statoDiPartenza;
	
	private ExtFlightDelaysDAO dao ;
	
	// VALORI IN OUTPUT	
	private int giorni;
	private Map<String, Integer> spostamenti; // stato - numero di persone
	
	public Simulatore(Graph<String, DefaultWeightedEdge> grafo, int T, int G) {
		this.grafo = grafo;
		this.T = T;
		this.G = G;
		dao = new ExtFlightDelaysDAO();
	}
	
	public void init(String partenza) {
		this.statoDiPartenza = partenza;
		
		this.giorni = 1;
		spostamenti = new HashMap<>();
		for(String s : dao.getStatiUSA()) {
			spostamenti.put(s, 0);
		}
		
		spostamenti.put(partenza, T);
		
		// creo la coda
		this.queue = new PriorityQueue<Evento>();
		
		// inserisco il primo evento 
		for(int i=1; i<=this.T; i++) {
			Evento e = new Evento(i, 1, partenza);
			this.queue.add(e);
		}
	}
	
	public void run(){
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		
		System.out.println("Sto processando l'evento "+e.toString());
		String statoPartenza = e.getStato();
		
		if(e.getG()<G) {
			String nuovoStato = scegliDestinazione(statoPartenza);
				
				if(nuovoStato!=null) {
					System.out.println(" trovato");
					queue.add(new Evento(e.getTuristaID(), e.getG()+1, nuovoStato));
					System.out.println("Ho aggiunto l'evento "+e.toString());
					this.spostamenti.put(nuovoStato, spostamenti.get(nuovoStato)+1);
					this.spostamenti.put(statoPartenza, spostamenti.get(statoPartenza)-1);
				} else {
					System.out.println("AAAAAAAAA");
				}
				
		}
	}

	private String scegliDestinazione(String statoPartenza) {
		
		List<DefaultWeightedEdge> vicini = new ArrayList<>(this.grafo.outgoingEdgesOf(statoPartenza));
		double pesoTot = 0.0;
		for(DefaultWeightedEdge e : vicini) {
			pesoTot += this.grafo.getEdgeWeight(e);
		}
		
		System.out.println(pesoTot);
		double probabilita = Math.random();
		System.out.println(probabilita);
		
		if(vicini.size()>1) {
			if(probabilita>=0 && probabilita<=(this.grafo.getEdgeWeight(vicini.get(0))/pesoTot)) {
				return this.grafo.getEdgeTarget(vicini.get(0));
			} else {
				for(int i = 0; i<vicini.size()-1; i++) {
					if(probabilita>=(this.grafo.getEdgeWeight(vicini.get(i))/pesoTot) 
							&& probabilita<=(this.grafo.getEdgeWeight(vicini.get(i+1))/pesoTot)) {
						return this.grafo.getEdgeTarget(vicini.get(i+1));
					}
					if(probabilita>=(this.grafo.getEdgeWeight(vicini.get(vicini.size()-1))/pesoTot)) {
						return this.grafo.getEdgeTarget(vicini.get(vicini.size()-1));
					}
							
				}
			}
		} else if(vicini.size()==1){
			return this.grafo.getEdgeTarget(vicini.get(0));
		} else {
			return statoPartenza;
		}
		return null;
	}
	
	public Map<String, Integer> getSpostamenti(){
		return this.spostamenti;
	}

}
