package in.ineuron.assignment1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import in.ineuron.jdbcUtil.JdbcUtil;

public class DateInsertionApp {
   public static void main(String[] args) throws ParseException {
      
	   Connection connection = null;
       PreparedStatement preparedStatement = null;
       Scanner scanner = new Scanner(System.in);
       
       System.out.println("Enter the name: ");
       String name = scanner.next();
       
       System.out.println("Enter the gender: ");
       String gender = scanner.next();
       
       System.out.println("Enter the address: ");
       String address = scanner.next();
       
       System.out.println("enter the dob(dd-mm-yyyy): ");
       String dob = scanner.next();
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
       java.util.Date utilDob = sdf.parse(dob);
       java.sql.Date sqlDob = new java.sql.Date(utilDob.getTime());
       
       System.out.println("enter the doj(dd-mm-yyyy): ");
       String doj = scanner.next();
       SimpleDateFormat sdfDoj = new SimpleDateFormat("MM-dd-yyyy");
       java.util.Date utilDoj = sdf.parse(doj);
       doj = sdfDoj.format(utilDoj);
       utilDoj = sdfDoj.parse(doj);
       java.sql.Date sqlDoj = new java.sql.Date(utilDoj.getTime());
       
       System.out.println("enter the dom(dd-mm-yyyy): ");
       String dom = scanner.next();
       SimpleDateFormat sdfDom = new SimpleDateFormat("yyyy-MM-dd");
       java.util.Date utilDom = sdf.parse(dom);
       dom = sdfDom.format(utilDom);
       utilDom = sdfDom.parse(dom);
       java.sql.Date sqlDom = new java.sql.Date(utilDom.getTime());
       
       String sqlInsertQuery = "insert into Employee(`name`,`gender`,`address`,`dob`,`doj`,`dom`) values(?,?,?,?,?,?)";
       
       try {
          connection = JdbcUtil.getJdbcConnection();
          if(connection != null)
            preparedStatement = connection.prepareStatement(sqlInsertQuery);
            if(preparedStatement != null) {
               preparedStatement.setString(1,name);
               preparedStatement.setString(2,gender);
               preparedStatement.setString(3,address);
               preparedStatement.setDate(4, sqlDob);
               preparedStatement.setDate(5, sqlDoj);
               preparedStatement.setDate(6, sqlDom);
               
               int NoOfRows = preparedStatement.executeUpdate();
               if(NoOfRows == 1)
                  System.out.println("Record insertion successfull...");
               else
                  System.out.println("Record insertion failed...");
            }
       } catch(SQLException se) {
          se.printStackTrace();
       } catch(Exception e) {
          e.printStackTrace();
       } finally {
          try {
             JdbcUtil.closeConnection(null, preparedStatement, connection);
          } catch(SQLException se) {
             se.printStackTrace();
          }
          if(scanner != null) {
             scanner.close();
          }
       }
    }
}