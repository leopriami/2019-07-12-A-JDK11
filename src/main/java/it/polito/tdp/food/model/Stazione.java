package it.polito.tdp.food.model;

public class Stazione {
	
	private Vertex cibo;
	private boolean libera;
	
	public Stazione(Vertex cibo, boolean libera) {
		super();
		this.cibo = cibo;
		this.libera = libera;
	}
	
	public Vertex getCibo() {
		return cibo;
	}
	
	public void setCibo(Vertex cibo) {
		this.cibo = cibo;
	}
	
	public boolean isLibera() {
		return libera;
	}
	
	public void setLibera(boolean libera) {
		this.libera = libera;
	}

}
