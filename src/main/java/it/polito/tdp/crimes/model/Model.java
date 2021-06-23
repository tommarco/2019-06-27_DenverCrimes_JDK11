package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDAO;

public class Model {
	
	Graph <String,DefaultWeightedEdge>grafo;
	EventsDAO dao;
	Map<Long,Event>idMap;
	
	
	public Model () {
		
		dao= new EventsDAO();
		idMap= new HashMap<Long,Event>();
		
	
		
	}

	public void creaGrafo(int a, String e) {
		
	grafo= new 	SimpleWeightedGraph <>(DefaultWeightedEdge.class);
	
	
	Graphs.addAllVertices(grafo, dao.getVertici(a, e) );
		
	
		for(Adiacenza aa: dao.getArchi(a,e)) {
			
			if(aa.getPeso()!=0) {
				
				
				if(grafo.containsVertex(aa.getR1()) && grafo.containsVertex(aa.getR2()) ){
					
					Graphs.addEdgeWithVertices(this.grafo,aa.getR1(), aa.getR2(), aa.getPeso());
					
					
				}
				
				
			}
			
			
		}
		
		
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}


	public List <String> getEvents(){
		
		return dao.getEvents();
	}
}
