package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;

public class Simulator {
	
	private Model model;
	
	private int K = 5;
	
	private Graph<Vertex, DefaultWeightedEdge> grafo;
	private List<Vertex> cibi;
	private List<Stazione> stazioni;
	
	private Double totalTime;
	private int cibiPreparati;
	
	private PriorityQueue<Event> queue;
	
	public void init(Model model, int K, Vertex cibo1) {
		this.K = K;
		this.model = model;
		this.grafo = model.getGrafo();
		this.cibi = new ArrayList<>();
		this.stazioni = new ArrayList<>();
		for(int i  = 0; i < this.K; i++) {
			Stazione s = new Stazione(null, true);
			stazioni.add(s);
		}
		this.totalTime = 0.0;
		this.cibiPreparati = 0;
		this.queue = new PriorityQueue<>();
		int i = 0;
		for(DefaultWeightedEdge e : this.model.grassi(cibo1)) {
			if(i<K) {
    			Event ev = new Event(EventType.INIZIO, grafo.getEdgeTarget(e), 0.0, grafo.getEdgeWeight(e), stazioni.get(i));
    			queue.add(ev);
    			i++;
    		}
		}
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Event e) {

		switch (e.getType()) {
		case INIZIO:
			e.getStazione().setLibera(false);
			cibi.add(e.getFood());
			for(DefaultWeightedEdge ed : this.model.grassi(e.getFood())) {
				if(!cibi.contains(grafo.getEdgeTarget(ed))) {
	    			Event ev = new Event(EventType.INIZIO, grafo.getEdgeTarget(ed), e.getFinishTime(), grafo.getEdgeWeight(ed)+e.getFinishTime(), e.getStazione());
	    			queue.add(ev);
	    		}
			}
			cibiPreparati++;
			totalTime = e.getFinishTime();
			break;

		case FINE:
			break;
		}
	}

	public Double getTotalTime() {
		return totalTime;
	}

	public int getCibiPreparati() {
		return cibiPreparati;
	}

}
