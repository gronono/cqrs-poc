package poc.cqrs.command;

import java.util.UUID;

public class Aggregate {

	private UUID id;
	private String type;
	
	public Aggregate(String type, UUID id) {
		this.type = type;
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Aggregate [id=%s, type=%s]", id, type);
	}
}
