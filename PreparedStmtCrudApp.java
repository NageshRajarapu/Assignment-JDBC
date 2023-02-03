package in.ineuron.assignment2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import in.ineuron.jdbcUtil.JdbcUtil;

public class PreparedStmtCrudApp {
	public static void main(String []args) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner scanner = new Scanner(System.in);
      
		try {
			while(true) {
				System.out.println("1.Create");
				System.out.println("2.Read");
				System.out.println("3.Update");
				System.out.println("4.Delete");
				System.out.println("5.Exit");
				System.out.println("Select an option from 1,2,3,4,5");
				int option = scanner.nextInt();
            
				switch(option) {
					case 1:
						System.out.println("Enter the name: ");
						String name = scanner.next();
						System.out.println("Enter the age: ");
						int age = scanner.nextInt();
						System.out.println("Enter the address: ");
						String address = scanner.next();
						String sqlInsertQuery = "insert into Student(`sname`,`sage`,`saddr`) values(?,?,?)";
						connection = JdbcUtil.getJdbcConnection();
							if(connection != null)
								preparedStatement = connection.prepareStatement(sqlInsertQuery);
							if(preparedStatement != null) {
								preparedStatement.setString(1,name);
								preparedStatement.setInt(2,age);
								preparedStatement.setString(3,address);
								int rowsAffected = preparedStatement.executeUpdate();
								if(rowsAffected == 1)
									System.out.println("Record Insertion successfull...");
								else
									System.out.println("Record Insertion failed...");                        	
							}
						break;
					case 2:
						System.out.println("Enter the sid: ");
						int id = scanner.nextInt();
						String sqlselectQuery = "select sid,sname,sage,saddr from Student where sid=?";
						connection = JdbcUtil.getJdbcConnection();
						if(connection != null)
							preparedStatement = connection.prepareStatement(sqlselectQuery);
						if(preparedStatement != null) {
							preparedStatement.setInt(1, id);
							resultSet = preparedStatement.executeQuery();
						}
						if(resultSet != null) {
							if(resultSet.next()) {
								System.out.println("SID\tSNAME\tSAGE\tSADDR");
								System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2)+"\t"+resultSet.getInt(3)+"\t"+resultSet.getString(4));
							}
							else {
								System.out.println("Record is not availabe for the given id: "+id);
							}
						}
						break;
					case 3:
						System.out.println("Enter the sid: ");
						int sid = scanner.nextInt();
						String sqlSelectQuery = "select sid,sname,sage,saddr from Student where sid=?";
						connection = JdbcUtil.getJdbcConnection();
						if(connection != null)
							preparedStatement = connection.prepareStatement(sqlSelectQuery);
						if(preparedStatement != null) {
							preparedStatement.setInt(1, sid);
							resultSet = preparedStatement.executeQuery();
						}
						if(resultSet != null) {
							if(resultSet.next()) {
								if(sid == resultSet.getInt(1)) {
									System.out.println("Enter the age: ");
									int sage = scanner.nextInt();
									System.out.println("Enter the address: ");
									String saddress = scanner.next();
									String sqlUpdateQuery = "update Student set sage=?,saddr=? where sid=?";
									preparedStatement = connection.prepareStatement(sqlUpdateQuery);
									if(preparedStatement != null) {
										preparedStatement.setInt(1,sage);
										preparedStatement.setString(2, saddress);
										preparedStatement.setInt(3,sid);
										int rowsAffected = preparedStatement.executeUpdate();
										if(rowsAffected == 1)
											System.out.println("Recored updation successfull...");
									}
								}
							}
							else {
								System.out.println("Record is not available to update for the given id: "+sid);
							}
						}
						break;
					case 4:
						System.out.println("Enter the id");
						int studid = scanner.nextInt();
						String sqlDeleteQuery = "delete from Student where sid=?";
						connection = JdbcUtil.getJdbcConnection();
						if(connection != null)
							preparedStatement = connection.prepareStatement(sqlDeleteQuery);
						if(preparedStatement != null) {
							preparedStatement.setInt(1, studid);
							int rowsAffected = preparedStatement.executeUpdate();
							if(rowsAffected == 1)
								System.out.println("Recored deletion successfull...");
							else
								System.out.println("Record is not availabe to delete for the given id: "+studid);
						}
						break;
					case 5:
						System.out.println("Thank you for using the Application");
						System.exit(0);
					default:
						System.out.println("Please select an option from 1,2,3,4,5 to perform an operation");
						break;
				}
			}
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.closeConnection(resultSet, preparedStatement, connection);
			} catch(SQLException se) {
				se.printStackTrace();
			}
			if(scanner != null) {
				scanner.close();
			}
		}
	}
}

