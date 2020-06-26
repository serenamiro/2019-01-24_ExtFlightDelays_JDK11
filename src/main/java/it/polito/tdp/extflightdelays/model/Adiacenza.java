package it.polito.tdp.extflightdelays.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private String s1;
	private String s2;
	private Integer peso;
	
	public Adiacenza(String s1, String s2, Integer peso) {
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return -(peso-o.getPeso());
	}

	@Override
	public String toString() {
		return s1 + " - " + s2 + " (peso=" + peso+")\n";
	}
	
	
	
}
