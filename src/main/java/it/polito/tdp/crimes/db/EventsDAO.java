package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;

public class EventsDAO {

	public void listAllEvents(Map<Long, Event> idMap) {
		String sql = "SELECT * FROM events";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {

					if (!idMap.containsKey(res.getLong("incident_id"))) {
						Event event = new Event(res.getLong("incident_id"), res.getInt("offense_code"),
								res.getInt("offense_code_extension"), res.getString("offense_type_id"),
								res.getString("offense_category_id"),
								res.getTimestamp("reported_date").toLocalDateTime(), res.getString("incident_address"),
								res.getDouble("geo_lon"), res.getDouble("geo_lat"), res.getInt("district_id"),
								res.getInt("precinct_id"), res.getString("neighborhood_id"), res.getInt("is_crime"),
								res.getInt("is_traffic"));

						idMap.put(event.getIncident_id(), event);
					}

				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public List<String> getVertici(int a, String e) {

		String sql = "SELECT DISTINCT e.offense_type_id as offense " + 
				"FROM events e " + 
				"WHERE YEAR(e.reported_date)=?   " + 
				"			AND e.offense_category_id=?  ";
		List<String> result = new LinkedList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a);
			st.setString(2, e);
			ResultSet res = st.executeQuery();

			while (res.next()) {

				result.add(res.getString("offense"));

			}
			conn.close();
			return result;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public List<Adiacenza> getArchi(int a, String e) {

		String sql = "SELECT e1.offense_type_id AS R1, e2.offense_type_id AS R2, COUNT( DISTINCT e1.district_id) AS PESO "
				+ "FROM `events` e1, `events` e2 " + "WHERE e1.incident_id> e2.incident_id "
				+ "		AND	YEAR(e1.reported_date)=? " + "		AND YEAR (e2.reported_date)=? "
				+ "			AND e1.offense_category_id=? " + "			AND e2.offense_category_id=? "
				+ "			AND e1.offense_category_id=e2.offense_category_id " + "GROUP BY R1,R2 "
				+ "HAVING PESO <> 0 ";

		List<Adiacenza> result = new LinkedList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a);
			st.setInt(2, a);
			st.setString(3, e);
			st.setString(4, e);
			ResultSet res = st.executeQuery();

			while (res.next()) {

				result.add(new Adiacenza(res.getString("R1"), res.getString("R2"), res.getInt("PESO")));

			}
			conn.close();
			return result;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<String> getEvents() {

		String sql = "SELECT DISTINCT e.offense_category_id as reato " + "FROM `events` e  "
				+ "WHERE e.offense_category_id='traffic-accident' ";

		List<String> result = new LinkedList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {

				result.add(res.getString("reato"));

			}
			conn.close();
			return result;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}

	}
}
