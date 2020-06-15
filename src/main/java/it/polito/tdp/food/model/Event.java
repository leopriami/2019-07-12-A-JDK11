package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		INIZIO,
		FINE,
	}
	
	private EventType type;
	private Vertex food;
	private Double startTime;
	private Double finishTime;
	private Stazione stazione;

	public Event(EventType type, Vertex food, Double startTime, Double finishTime, Stazione stazione) {
		super();
		this.type = type;
		this.food = food;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.stazione = stazione;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Vertex getFood() {
		return food;
	}

	public void setFood(Vertex food) {
		this.food = food;
	}

	public Double getStartTime() {
		return startTime;
	}

	public void setStartTime(Double startTime) {
		this.startTime = startTime;
	}

	public Double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Double finishTime) {
		this.finishTime = finishTime;
	}

	public Stazione getStazione() {
		return stazione;
	}

	public void setStazione(Stazione stazione) {
		this.stazione = stazione;
	}

	@Override
	public int compareTo(Event o) {
		return startTime.compareTo(o.startTime);
	}

}
