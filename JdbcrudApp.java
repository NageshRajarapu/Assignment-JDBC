package in.ineuron.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcrudApp {

	public static void main(String[] args) throws SQLException {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Scanner scanner = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost/JavaJDBC";
		String user = "root";
		String password = "Anvi@240520";
		
		try {
			while(true) {
				System.out.println("1.Create");
				System.out.println("2.Read");
				System.out.println("3.Update");
				System.out.println("4.Delete");
				System.out.println("5.Exit");
				System.out.println("Enter the option from 1,2,3,4,5 to perform an operation");
				int option = scanner.nextInt();
				
				switch(option) {
					case 1:
						connection = DriverManager.getConnection(url, user, password);
						if(connection != null) {
							statement = connection.createStatement();
							if(statement != null) {
								System.out.println("Enter the name: ");
								String name = scanner.next();
								System.out.println("enter the age: ");
								int age = scanner.nextInt();
								System.out.println("enter the address: ");
								String address = scanner.next();
								String sqlInsertQuery = String.format("insert into Student(`sname`,`sage`,`saddr`)values('%s','%d','%s')",name,age,address);
								int NoOfRows = statement.executeUpdate(sqlInsertQuery);
								if(NoOfRows ==1 )
									System.out.println("Record inserted successfully...");
								else
									System.out.println("Record insertion failed...");
							}
						}
						break;
					case 2:
						connection = DriverManager.getConnection(url, user, password);
						if(connection != null) {
							statement = connection.createStatement();
							if(statement != null) {
								System.out.println("enter the id to read the record");
								int sid = scanner.nextInt();
								String sqlSelectQuery = String.format("select sid,sname,sage,saddr from Student where sid='%d'",sid);
								resultSet = statement.executeQuery(sqlSelectQuery);
								System.out.println("SID\tSNAME\tSAGE\tSADDR");
								if(resultSet != null) {
									if(resultSet.next()) {
										int id = resultSet.getInt(1);
										String name = resultSet.getString(2); 
										int age = resultSet.getInt(3);
										String address = resultSet.getString(4);
										System.out.println(id+"\t"+name+"\t"+age+"\t"+address);
									}
									else
										System.out.println("Record not available for the given id: "+sid);
								}
							}
						}
						break;
					case 3:
						
						System.out.println("enter the id to update record");
						int id = scanner.nextInt();
						String sqlSelectQuery = String.format("select sid from Student where sid='%d'", id);
						connection = DriverManager.getConnection(url, user, password);
						if(connection != null)
							statement = connection.createStatement();
						if(statement != null)
							resultSet = statement.executeQuery(sqlSelectQuery);
						if(resultSet != null) {
							if(resultSet.next()) {
								if(id == resultSet.getInt(1)) {
									System.out.println("Enter the name: ");
									String name = scanner.next();
									System.out.println("enter the age: ");
									int age = scanner.nextInt();
									System.out.println("enter the address: ");
									String address = scanner.next();
									String sqlUpdateQuery = String.format("update Student set sname='%s',sage='%d',saddr='%s' where sid='%d'", name,age,address,id);
									int NoOfRows = statement.executeUpdate(sqlUpdateQuery);
									if(NoOfRows == 1)
										System.out.println("Record updated successfully...");
								}
							}
							else
								System.out.println("Record is not available to update for the given id: "+id);
						}						
						break;
					case 4:
						connection = DriverManager.getConnection(url, user, password);
						if(connection != null) {
							statement = connection.createStatement();
							if(statement != null) {
								System.out.println("enter the id to delete record");
								int sid = scanner.nextInt();
								String sqlDeleteQuery = String.format("delete from Student where sid='%d'", sid);
								int NoOfRows = statement.executeUpdate(sqlDeleteQuery);
								if(NoOfRows == 1)
									System.out.println("Record deleted suceessfully");
								else
									System.out.println("Record is not available to delete for the given id: "+sid);
							}
						}
						break;
					case 5:
						System.out.println("Thank you for using tha application");
						System.exit(0);
					default:
						System.out.println("Please enter the number from 1,2,3,4,5 for operation");
						break;
				}
			}
			
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(resultSet != null)
				resultSet.close();
			if(statement != null)
				statement.close();
			if(connection != null)
				connection.close();
			if(scanner != null)
				scanner.close();
		}
	}

}
