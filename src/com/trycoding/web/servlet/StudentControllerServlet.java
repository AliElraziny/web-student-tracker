package com.trycoding.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.trycoding.web.jdbc.StudentDbUtil;
import com.trycoding.web.model.Student;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	StudentDbUtil studentDbUtil ;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			
			studentDbUtil = new StudentDbUtil(dataSource);
		}catch(Exception e) {
			
			throw new ServletException();
			
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter("command");
		
		if(command == null) {
			
			command ="List";
		}
		
		switch (command) {
			case "List":
					try {
						listStudent(request,response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				break;
			case "Add":
					try {
						addStudent(request , response);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				break;
			case "LOAD":
				try {
					loadStudent(request , response);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			break;
			case "UPDATE":
				try {
					updateStudent(request , response);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			break;
			case "DELETE":
				try {
					deleteStudent(request , response);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			break;
				
				
			default:
					try {
						listStudent(request,response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
//		try{
//			listStudent(request,response);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int id =Integer.parseInt(request.getParameter("studentId"));
		
		studentDbUtil.deleteStudent(id);
		
		listStudent(request,response);
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id =Integer.parseInt(request.getParameter("studentId"));
		String firstName =request.getParameter("firstName");
		String lastName =request.getParameter("lastName");
		String email =request.getParameter("email");
		
		Student theStudent =new Student(id,firstName , lastName , email);
		
		studentDbUtil.updateStudent(theStudent);
		
		listStudent(request,response);
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response)
	
	throws Exception{
		
		String theStudentId = request.getParameter("studentId");
		
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		
		request.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);
		
		
				
		
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName =request.getParameter("firstName");
		String lastName =request.getParameter("lastName");
		String email =request.getParameter("email");
		
		Student theStudent =new Student(firstName , lastName , email);
		
		studentDbUtil.addStudent(theStudent);
		
		listStudent(request,response);
		
	}



	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		List<Student> students = studentDbUtil.getStudents();
		
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		
		dispatcher.forward(request, response);
		
	}

}
