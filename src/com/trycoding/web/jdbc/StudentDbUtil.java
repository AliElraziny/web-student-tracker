package com.trycoding.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.trycoding.web.model.Student;

public class StudentDbUtil {

	
	private DataSource datasource ;

	public StudentDbUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public List<Student> getStudents() throws Exception{
		
		List<Student> students = new ArrayList();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs =null ;
		
		try {
//			myConn =DbCon.getConnetion();
			myConn =datasource.getConnection();
			String sql =" select * from student order by last_name" ;
			myStmt = myConn.createStatement();
		    myRs=myStmt.executeQuery(sql);
		    
		    while(myRs.next()) {
		    	
		    	int id = myRs.getInt("id");
		    	String firstName=myRs.getString("first_name");
		    	String lastName=myRs.getString("last_name");
		    	String email =myRs.getString("email");
		    	
		    	Student tempStudent = new Student(id, firstName,lastName,email);
		    	
		    	students.add(tempStudent);
		    	
		    }
			 
		    return students;
			
		}finally {
			
			close(myConn ,myStmt,myRs);
		}
		
		
		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		
		
		try {
			
			if(myRs!=null) {
				
				myRs.close();
			}
			if(myStmt!=null) {
				
				myStmt.close();
			}
			if(myConn!=null) {
				
				myConn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void addStudent(Student theStudent) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		Connection myConn = null ;
		PreparedStatement myStmt = null;
		
		try {
//			myConn =DbCon.getConnetion();
			myConn = datasource.getConnection();
			String query ="insert into student "
					+"(first_name,last_name,email)"
					+"values(?,?,?)";
			myStmt= myConn.prepareStatement(query);
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			myStmt.execute();
			
			
			
			
			
		}finally {
			
			close(myConn,myStmt,null);
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception {

		Student theStudent =null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null ;
		ResultSet myRs = null;
		int studentId ; 
		
		try 
		{
			
			studentId =Integer.parseInt(theStudentId);
			myConn=datasource.getConnection();
			
			String query = "select * from student where id = ?";
			
			myStmt = myConn.prepareStatement(query);
			
			myStmt.setInt(1, studentId);
			
			myRs=myStmt.executeQuery();
			
			if (myRs.next()) {
				
				
				String firstName=myRs.getString("first_name");
				String lastName=myRs.getString("last_name");
				String email=myRs.getString("email");
				
				theStudent =new Student(studentId,firstName,lastName,email);
				
			}else {
				
				throw new Exception("couldn't find student " +studentId);
				
			}
			
			
			
			return theStudent ;
			
		}finally {
			
			close(myConn ,myStmt,myRs);
		}
		
	}

	public void updateStudent(Student theStudent) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null ;
		
		try 
		{
			myConn=datasource.getConnection();
			
			String query = "update student "
					+"set first_name=?, last_name=?, email=? "
					+"where id=?";

			
			myStmt = myConn.prepareStatement(query);
			
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			myStmt.execute();
			
			
		}finally {
			
			close(myConn ,myStmt,null);
		}
	}

	public void deleteStudent(int id) throws SQLException {

		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		
		try {
			myConn=datasource.getConnection();
			String query="delete from student where id =?";
			myStmt=myConn.prepareStatement(query);
			myStmt.setInt(1, id);
			myStmt.execute();
			
			
		}finally {
			close(myConn,myStmt,null);
		}
	}
	
	
}
