package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Edge;
import it.polito.tdp.food.model.Vertex;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Vertex> listAllFoods(int count){
		String sql = "select food.food_code, display_name from food, portion where food.food_code = portion.food_code group by food.food_code, display_name having count(food.food_code)>=? order by display_name ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Vertex> list = new ArrayList<>() ;
			
			st.setInt(1, count);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Vertex(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Edge> listAllEdges(Map<Integer, Vertex> idMap){
		String sql = "select p1.food_code as f1, p2.food_code as f2, (avg(p1.saturated_fats) - avg(p2.saturated_fats)) as peso " + 
				"from portion as p1, portion as p2 " + 
				"where p1.food_code <> p2.food_code " + 
				"group by p1.food_code, p2.food_code " + 
				"having avg(p1.saturated_fats) > avg(p2.saturated_fats) " + 
				"order by p1.food_code, p2.food_code ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Edge> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(idMap.get(res.getInt("f1")) != null && idMap.get(res.getInt("f2")) != null) {
						list.add(new Edge(idMap.get(res.getInt("f1")), idMap.get(res.getInt("f2")), res.getDouble("peso")));
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
}
