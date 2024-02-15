package com.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	private Connection connection;
	
	Doctor(Connection connection){
		this.connection = connection;
	}

	
	public void viewDoctor() throws SQLException {
		String query = "select * from doctors";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultset = statement.executeQuery();
		System.out.println("Doctors:");
		while(resultset.next()) {
			int id = resultset.getInt("id");
			String name = resultset.getString("name");
			int age = resultset.getInt("age");
			String specialization = resultset.getString("specialization");
			System.out.println(id + " " + name + " " + age + " " + specialization);
		}
	}
	
	
	public boolean getDoctorById(int id) {
	    try {
	        String query = "SELECT * FROM doctors WHERE id = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, id);
	        ResultSet resultSet = statement.executeQuery();
	        return resultSet.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
