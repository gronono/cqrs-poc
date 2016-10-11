package poc.cqrs.event.impl;

import static java.lang.String.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import poc.cqrs.event.EventStore;
import poc.cqrs.event.EventStoreEntry;

public class JdbcEventStore implements EventStore {

	private DataSource datasource;
	private String aggregateType;
	
	private ObjectMapper mapper = new ObjectMapper();

	public JdbcEventStore(String aggregateType, DataSource datasource) {
		this.aggregateType = aggregateType;
		this.datasource = datasource;
	}
	
	@Override
	public void save(EventStoreEntry entry) {
		String query = format("insert into %s (id, aggregate, created, type, payload) values (?, ?, ?, ?, ?)", tableName());
		try (Connection connection = datasource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, entry.getId().toString());
				statement.setString(2, entry.getAggregateId().toString());
				statement.setTimestamp(3, Timestamp.from(entry.getCreated()));
				statement.setString(4, entry.getPayload().getClass().getName());
				statement.setString(5, toJSON(entry.getPayload()));
				statement.executeUpdate();
			}
		} catch (SQLException | JsonProcessingException e) {
			// TODO Trouver une meilleur exception
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<EventStoreEntry> read(UUID aggregateId) {
		List<EventStoreEntry> result = new ArrayList<>();
		String query = format("select id, aggregate, created, type, payload from %s where aggregate = ? order by created", tableName());
		try (Connection connection = datasource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, aggregateId.toString());
				try (ResultSet rs = statement.executeQuery()) {
					while (rs.next()) {
						EventStoreEntry entry = new EventStoreEntry(
								UUID.fromString(rs.getString("id")), 
								UUID.fromString(rs.getString("aggregate")),
								rs.getTimestamp("created").toInstant(),
								fromJSON(rs.getString("type"), rs.getString("payload")));
						result.add(entry);
					}
				}
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			// TODO Trouver une meilleur exception
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@Override
	public String applyOn() {
		return this.aggregateType;
	}
	
	private String tableName() {
		return format("t_%s_events", this.aggregateType);
	}

	private String toJSON(Object event) throws JsonProcessingException {
		String json = mapper.writeValueAsString(event);
		return json;
	}

	private Object fromJSON(String type, String json) throws ClassNotFoundException, IOException {
		return mapper.readValue(json, Class.forName(type));
	}
}
