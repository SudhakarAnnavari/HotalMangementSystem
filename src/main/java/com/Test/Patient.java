package com.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Patient {
	private Connection connection;
	private Scanner scan;
	
	Patient(Connection connection,Scanner scan){
		this.connection = connection;
		this.scan = scan;
	}
	
	public void addPatient() {
		System.out.print("Enter pateint Name:");
		String name = scan.next();
		System.out.println("Enter pateint Age:");
		int age = scan.nextInt();
		System.out.println("Enter patient Gender");
		String gender = scan.next();
		
		try {
			String query = "Insert into patients(name,age,gender) values(?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,name);
			statement.setInt(2, age);
			statement.setString(3, gender);
			int result = statement.executeUpdate();
			if(result>0) {
				System.out.println("Patient added successfully");
			}
			else {
				System.out.println("Failed to add Patient");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void viewPatient() throws SQLException {
		String query = "select * from patients";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultset = statement.executeQuery();
		System.out.println("patients:");
		while(resultset.next()) {
			int id = resultset.getInt("id");
			String name = resultset.getString("name");
			int age = resultset.getInt("age");
			String gender = resultset.getString("gender");
			System.out.println(id + " " + name + " " + age + " " + gender);
		}
	}
	
	
	public boolean getPatientById(int id) throws SQLException {
	    String query = "select * from patients where id = ?";
	    PreparedStatement statement = connection.prepareStatement(query);
	    statement.setInt(1, id);
	    ResultSet resultset = statement.executeQuery(); 
	    if (resultset.next()) {
	        return true;
	    } else {
	        return false;
	    }
	}



}
