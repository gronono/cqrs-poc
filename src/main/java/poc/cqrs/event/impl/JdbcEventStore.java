package poc.cqrs.event.impl;

import static java.lang.String.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import poc.cqrs.event.EventStore;

public class JdbcEventStore implements EventStore {

	private DataSource datasource;
	private String aggregateType;
	
	private ObjectMapper mapper = new ObjectMapper();

	public JdbcEventStore(String aggregateType, DataSource datasource) {
		this.aggregateType = aggregateType;
		this.datasource = datasource;
	}
	
	@Override
	public void save(UUID aggregateId, Object event) {
		String query = format("insert into %s (id, aggregate, created, type, payload) values (?, ?, ?, ?, ?)", tableName());
		try (Connection connection = datasource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, UUID.randomUUID().toString());
				statement.setString(2, aggregateId.toString());
				statement.setTimestamp(3, Timestamp.from(Instant.now()));
				statement.setString(4, event.getClass().getName());
				statement.setString(5, toJSON(event));
				statement.executeUpdate();
			}
		} catch (SQLException | JsonProcessingException e) {
			// TODO Trouver une meilleur exception
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Object> read(UUID aggregateId) {
		List<Object> result = new ArrayList<>();
		String query = format("select id, aggregate, created, type, payload from %s where aggregate = ? order by created", tableName());
		try (Connection connection = datasource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, aggregateId.toString());
				try (ResultSet rs = statement.executeQuery()) {
					while (rs.next()) {
						String type = rs.getString("type");
						String payload = rs.getString("payload");
						result.add(fromJSON(type, payload));
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
