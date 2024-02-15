package com.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String name = "root";
	private static final String password = "jeswanth@10";
	
	public static void main(String[] args) {
		 Scanner scan = new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url,name,password);
			Patient patient = new Patient(connection,scan);
			Doctor doctor = new Doctor(connection);
			
			while(true) {
				System.out.println("Hospital Management System");
				System.out.println("1. Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. BookAppointment");
				System.out.println("5. Exit");
				System.out.println("Enter choice:");
				int choice = scan.nextInt();
				
				switch (choice) {
				case 1:
					patient.addPatient();
					break;
				case 2:
					patient.viewPatient();
					break;
				case 3:
					doctor.viewDoctor();
					break;
				case 4:
					bookAppontment(doctor, patient, connection, scan);
					break;
				case 5:
					return;
				default:
					System.out.println("Enter valid choice");
					break;
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void bookAppontment(Doctor doctor,Patient patient,Connection connection,Scanner scan) throws SQLException {
		System.out.println("Enter patient id:");
		int p_id = scan.nextInt();
		System.out.println("Enter doctor_id");
		int d_id = scan.nextInt();
		System.out.println("Enter Appointment Date (DD-MM-YYYY):");
		String date = scan.next();
		if(patient.getPatientById(p_id) && doctor.getDoctorById(d_id)) {
			if(checkDoctorAvailable(d_id,date,connection)) {
				String query = "insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setInt(1, p_id);
				statement.setInt(2,d_id);
				statement.setString(3, date);
				int result = statement.executeUpdate();
				if(result>0) {
					System.out.println("Appoitment Booked");
				}
				else {
					System.out.println("Failed to book appointment");
				}
			}
			else {
				System.out.println("Doctor not available on that date");
			}
		}
	}


	public static boolean checkDoctorAvailable(int d_id, String date,Connection connection) throws SQLException {
		String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, d_id);
		statement.setString(2, date);
		ResultSet resultset = statement.executeQuery();
		while(resultset.next()) {
			int count = resultset.getInt(1);
			if(count == 0) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

}
