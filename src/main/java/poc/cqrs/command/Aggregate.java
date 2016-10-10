package poc.cqrs.command;

import java.util.UUID;

public class Aggregate {

	private UUID id;
	private String type;
	
	public Aggregate(UUID id, String type) {
		this.id = id;
		this.type = type;
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
