package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private SimpleDirectedWeightedGraph<Vertex, DefaultWeightedEdge> grafo;
	private List<Vertex> nodi = new ArrayList<>();
	private List<Edge> archi = new ArrayList<>();
	private FoodDao dao;
	private Map<Integer, Vertex> idMap = new HashMap<>();
	private Simulator sim;
	
	public Model() {
		this.dao = new FoodDao();
		this.sim = new Simulator();
	}
	
	public void creaGrafo(int portions) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.nodi = dao.listAllFoods(portions);
		for(Vertex v : nodi) {
			grafo.addVertex(v);
			idMap.put(v.getFood_code(), v);
		}
		
		this.archi = dao.listAllEdges(idMap);
		System.out.println(nodi.size());
		System.out.println(archi.size());
		for(Edge e : archi) {
			if(nodi.contains(e.getV1()) && nodi.contains(e.getV2())) {
				Graphs.addEdge(grafo, e.getV1(), e.getV2(), e.getWeight());
			}
		}
	}

	public SimpleDirectedWeightedGraph<Vertex, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<DefaultWeightedEdge> grassi(Vertex v){
		List<DefaultWeightedEdge> grassi = new ArrayList<>(grafo.outgoingEdgesOf(v));
		Collections.sort(grassi, new Comparator<DefaultWeightedEdge>() {
			public int compare(DefaultWeightedEdge c1, DefaultWeightedEdge c2) {
				if(grafo.getEdgeWeight(c1) - grafo.getEdgeWeight(c2) > 0) {
					return 1;
				}
				else return -1;
			}
		});
		return grassi;
	}

	public Simulator getSim() {
		return sim;
	}

}
