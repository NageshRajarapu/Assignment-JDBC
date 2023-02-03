package in.ineuron.assignment1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import in.ineuron.jdbcUtil.JdbcUtil;

public class DateRetrievalApp {
   public static void main(String[] args) throws ParseException {
      
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the username: ");
      String userName = scanner.next();
      
      String sqlSelectQuery = "select gender,address,dob,doj,dom from Employee where name=?";
      
      try {
         connection = JdbcUtil.getJdbcConnection();
         if(connection != null)
            preparedStatement = connection.prepareStatement(sqlSelectQuery);
            if(preparedStatement != null) {
               preparedStatement.setString(1, userName);
               resultSet = preparedStatement.executeQuery();
            }
            if(resultSet != null) {
               if(resultSet.next()) {
                  String gender = resultSet.getString(1);
                  String address = resultSet.getString(2);
                  java.sql.Date sqlDOB = resultSet.getDate(3);
                  SimpleDateFormat sdfDOB = new SimpleDateFormat("dd-MM-yyyy");
                  String DOB = sdfDOB.format(sqlDOB);
                  java.sql.Date sqlDOJ = resultSet.getDate(4);
                  SimpleDateFormat sdfDOJ = new SimpleDateFormat("MM-dd-yyyy");
                  String DOJ = sdfDOJ.format(sqlDOJ);
                  java.sql.Date sqlDOM = resultSet.getDate(5);
                  SimpleDateFormat sdfDOM = new SimpleDateFormat("yyyy-MM-dd");
                  String DOM = sdfDOM.format(sqlDOM);
                  System.out.println("Gender\t: "+gender);
                  System.out.println("Address\t: "+address);
                  System.out.println("DOB\t: "+DOB);
                  System.out.println("DOJ\t: "+DOJ);
                  System.out.println("DOM\t: "+DOM);
               }
            } else {
               System.out.println("Record not available for the given name"+userName);
            }
      } catch(SQLException se) {
            se.printStackTrace();
      } catch(Exception e) {
            e.printStackTrace();
      } finally {
         try {
            JdbcUtil.closeConnection(resultSet,preparedStatement,connection);
         } catch(SQLException se) {
               se.printStackTrace();
         }
         if(scanner != null) {
               scanner.close();
         }
      }
   }
}