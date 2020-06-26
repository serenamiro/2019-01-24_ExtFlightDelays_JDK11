package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> stati;
	private Simulatore sim;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		stati = dao.getStatiUSA();
		
		Graphs.addAllVertices(this.grafo, stati);
		
		List<Adiacenza> adiacenze = dao.getStatiConVoli();
		for(Adiacenza a : adiacenze) {
			Integer peso = dao.getPesoArco(a.getS1(), a.getS2());
			Graphs.addEdgeWithVertices(this.grafo, a.getS1(), a.getS2(), peso);
		}
		System.out.format("Grafo creato con %d vertici e %d archi\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getStati(){
		Collections.sort(this.stati);
		return stati;
	}
	
	public List<Adiacenza> getVicini(String stato){
		Set<DefaultWeightedEdge> vicini = this.grafo.outgoingEdgesOf(stato);
		List<Adiacenza> adiacenti = new ArrayList<>();
		
		for(DefaultWeightedEdge e : vicini) {
			adiacenti.add(new Adiacenza(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int)this.grafo.getEdgeWeight(e)));
		}
		
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	public Map<String, Integer> doSimulazione(String partenza, int T, int G){
		sim = new Simulatore(this.grafo, T, G);
		sim.init(partenza);
		sim.run();
		Map<String, Integer> result = sim.getSpostamenti();
		return result;
	}
	
}
