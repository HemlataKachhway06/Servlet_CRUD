package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Employee;

public class DBOperation {
	static Connection con = null;
	
	private static void connect() {
		
		String url = "jdbc:mysql://localhost:3306/java330";
		String id = "root";
		String pass = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, pass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean insert(Employee e) {
		
		connect();
		boolean b = false;
		
		try {
			PreparedStatement ps = con.prepareStatement("insert into employees value(?,?,?,?)");
			ps.setInt(1, e.getId());
			ps.setString(2, e.getName());
			ps.setString(3, e.getDepartment());
			ps.setInt(4, e.getSalary());
			
			b = ps.execute();
			
//			if(!ps.execute()) {
			
//				System.out.println("Data inserted successfully....");
//			}
//			else {
//				System.out.println("Data not inserted !");
//			}
			con.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}
	
public static boolean delete(int id) {
		
		connect();
		boolean b = false;
		
		try {
			PreparedStatement ps = con.prepareStatement("delete from employees where id=?");
			ps.setInt(1, id);
			
			if(!ps.execute()) {
				System.out.println("Data deleted successfully....");
			}
			else {
				System.out.println("Data not deleted !");
			}	
			con.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}
	
public static ArrayList<Employee> view(Employee e) {
	
	connect();
	ArrayList<Employee> al = new ArrayList<Employee>();
	
	try {
		PreparedStatement ps = con.prepareStatement("select * from employees");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
		e.setId(rs.getInt(1));
		e.setName(rs.getString(2));
		e.setDepartment(rs.getString(3));
		e.setSalary(rs.getInt(4));
		al.add(e);
		}
		con.close();
	}
	catch (Exception ex) {
		ex.printStackTrace();
}
	return al;
}

public static Employee getDataById(int id) {
	
	connect();
	Employee e = new Employee();
	
	try {
		PreparedStatement ps = con.prepareStatement("select * from employees where id = ?");
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
		e.setId(rs.getInt(1));
		e.setName(rs.getString(2));
		e.setDepartment(rs.getString(3));
		e.setSalary(rs.getInt(4));
		}
		con.close();
	}
	catch (Exception ex) {
		ex.printStackTrace();
}
	return e;
}

public static int update(Employee e) {
	
	connect();
	int v = 0;
	
	try {
		
		PreparedStatement ps = con.prepareStatement("update employees set name=?,department=?,salary=? where id=?");
		ps.setString(1, e.getName());
		ps.setString(2, e.getDepartment());
		ps.setInt(3, e.getSalary());
		ps.setInt(4, e.getId());
		
		v = ps.executeUpdate();
		
		con.close();
		}
	catch (Exception ex) {
		ex.printStackTrace();
}
	return v;
}

public static boolean login(String id, String password) {
	connect();
	boolean b = false;
	
	try {
		
		PreparedStatement ps = con.prepareStatement("select * from login where id=? and password=?");
		ps.setString(1, id);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			b=true;
		}	
		con.close();
		}
	catch (Exception ex) {
		ex.printStackTrace();
}
	return b;
}
}