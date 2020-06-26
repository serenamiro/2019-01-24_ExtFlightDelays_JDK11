package it.polito.tdp.extflightdelays.model;

public class Evento implements Comparable<Evento>{
	
	private int TuristaID;
	private int G; // NUMERO DI GIORNO 
	private String stato; // stato in cui arriva
	
	public Evento(int turistaID, int g, String stato) {
		this.TuristaID = turistaID;
		this.G = g;
		this.stato = stato;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getTuristaID() {
		return TuristaID;
	}

	public void setTuristaID(int turistaID) {
		TuristaID = turistaID;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return -G+o.getG();
	}

	@Override
	public String toString() {
		return TuristaID + ", " + G + ", " + stato;
	}
	
	
	
	
}
