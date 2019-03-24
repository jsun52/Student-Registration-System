// package jdbc_proj2.utility;
package testjs;
import java.sql.*;
import oracle.jdbc.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;
import java.util.ArrayList;

public class JdbcUtilities {
    //setting the connection info
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "scott";
    private static final String PASSWORD = "*******";
    private static final int TABLE_LIMIT = 100;
    //get the connection
    private static Connection getConnection(){
      OracleDataSource ds = null;
      Connection conn = null;
      CallableStatement cs = null;
        //exceptions
        try {
            ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL(URL);
            conn = ds.getConnection(USER, PASSWORD);
        }
        catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());}
        catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
        return conn;
    }

    /** question 2 */
    //get all the students from the sql db
    public static Object[][] showStudents() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_STUDENTS(?); end;"; //setting query
      CallableStatement cs = null;
      try {
        Connection conn = getConnection(); //connect to sql db
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        //getting all info from a student
        while (res.next()) {
          Object[] obj = new Object[8];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          obj[2] = res.getString(3);
          obj[3] = res.getString(4);
          obj[4] = res.getDouble(5);
          obj[5] = res.getString(6);
          obj[6] = res.getTimestamp(7);
          obj[7] = res.getString(8);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][8];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    //getting all TAs information
    public static Object[][] showTAs() {
        ArrayList<Object[]> l = new ArrayList<Object[]>();
        String query = "begin fun.show_TAS(?); end;";
        CallableStatement cs = null;
        try {
          Connection conn = getConnection();
          cs = conn.prepareCall(query);
          cs.registerOutParameter(1, OracleTypes.CURSOR);
          cs.execute();
          ResultSet res = (ResultSet)cs.getObject(1);
          while (res.next()) {
            Object[] obj = new Object[3];
            obj[0] = res.getString(1);
            obj[1] = res.getString(2);
            obj[2] = res.getString(3);
            l.add(obj);
          }
          cs.close();
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        Object[][] rv = new Object[l.size()][3];
        for (int i = 0; i < l.size(); ++i) {
          for (int j = 0; j < l.get(i).length; ++j) {
            rv[i][j] = l.get(i)[j];
          }
        }
        return rv;
      }
    
    //getting all the pre-req from sql db
    public static Object[][] showPrerequisites() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_PREREQUISITES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[4];
          obj[0] = res.getString(1);
          obj[1] = res.getInt(2);
          obj[2] = res.getString(3);
          obj[3] = res.getInt(4);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][4];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    //getting all log info from sql db
    public static Object[][] showLogs() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_LOGS(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        int count = 0;
        while (res.next()) {
          Object[] obj = new Object[6];
          obj[0] = res.getInt(1);
          obj[1] = res.getString(2);
          obj[2] = res.getTimestamp(3);
          obj[3] = res.getString(4);
          obj[4] = res.getString(5);
          obj[5] = res.getString(6);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][6];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    //getting grades from sql db
    public static Object[][] showGrades() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_GRADES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[2];
          obj[0] = res.getString(1);
          obj[1] = res.getInt(2);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][2];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    //getting all enroll from sql db
    public static Object[][] showEnrollments() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_ENROLLMENTS(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[3];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          obj[2] = res.getString(3);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][3];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }
    
    public static Object[][] showCourseCredit() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_COURSE_CREDIT(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[2];
          obj[0] = res.getInt(1);
          obj[1] = res.getInt(2);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][2];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < 2; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
      // return l.toArray();
    }
//getting all courses from sql db
    public static Object[][] showCourses() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_COURSES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[3];
          obj[0] = res.getString(1);
          obj[1] = res.getInt(2);
          obj[2] = res.getString(3);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      // return l.toArray();
      Object[][] rv = new Object[l.size()][3];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < 3; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }
//getting all classes from sql db
    public static Object[][] showClasses() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.show_CLASSES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[10];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          obj[2] = res.getInt(3);
          obj[3] = res.getInt(4);
          obj[4] = res.getInt(5);
          obj[5] = res.getString(6);
          obj[6] = res.getInt(7);
          obj[7] = res.getInt(8);
          obj[8] = res.getString(9);
          obj[9] = res.getString(10);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][10];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question no */
    //insert a student into the sql db
    public static String insertStudent(String classid, String dept_code, int course_no, int sect_no, int year, String semester, int limit, int class_size, String room, String TA_B_no) {
      String rv = "";
      String query = "begin fun.insert_a_student(?,?,?,?,?,?,?,?,?,?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, classid);
        cs.setString(2, dept_code);
        cs.setInt(3, course_no);
        cs.setInt(4, sect_no);
        cs.setInt(5, year);
        cs.setString(6, semester);
        cs.setInt(7, limit);
        cs.setInt(8, class_size);
        cs.setString(9, room);
        cs.setString(10, TA_B_no);
        cs.registerOutParameter(11, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(11);
        while (res.next()) {
          rv =
          res.getString(1) + "\t" +
          res.getString(2) + "\t" +
          res.getInt(3) + "\t" +
          res.getInt(4) + "\t" +
          res.getInt(5) + "\t" +
          res.getString(6) + "\t" +
          res.getInt(7) + "\t" +
          res.getInt(8) + "\t" +
          res.getString(9) + "\t" +
          res.getString(10) + "\n";
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return rv;
    }

    /** question 3 */
    //getting ta from class
    public static Object[][] getClassOfStudent(String classid, ArrayList<String> message) {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.get_student_classes(?,?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, classid);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.registerOutParameter(3, OracleTypes.CURSOR);
        cs.registerOutParameter(4, java.sql.Types.VARCHAR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(2);
        while (res.next()) {
          message.add(res.getString(1)+"\t");
          message.add(res.getString(2)+"\t");
          message.add(res.getInt(3)+"\t");
          message.add(res.getInt(4)+"\t");
          message.add(res.getInt(5)+"\t");
          message.add(res.getString(6)+"\t");
          message.add(res.getInt(7)+"\t");
          message.add(res.getInt(8)+"\t");
          message.add(res.getString(9)+"\t");
          message.add(res.getString(10));
          
        }
        ResultSet TAs = (ResultSet)cs.getObject(3);
        while (TAs.next()) {
          Object[] obj = new Object[8];
          obj[0] = TAs.getString(1);
          obj[1] = TAs.getString(2);
          obj[2] = TAs.getString(3);
          l.add(obj);
        }
        String err_message = cs.getString(4);
        if (err_message != null) {
          message.add(err_message);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][3];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      
      return rv;
    }
    /** question 5 */
    //getting all pre-req from sql db
    public static Object[][] getPrerequisite(String dept_code, String course_no) {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.get_prerequisite(?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, dept_code);
        cs.setString(2, course_no);
        cs.registerOutParameter(3, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(3);
        while (res.next()) {
          Object[] obj = new Object[2];
          obj[0] = res.getString(1);
          obj[1] = res.getInt(2);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][2];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question 6 */
    //getting all student who are taking one class
    public static Object[][] getStudentsOfClass(String cid, ArrayList<String> cla) {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin fun.get_enrolledOf(?,?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, cid);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.registerOutParameter(3, OracleTypes.CURSOR);
        cs.registerOutParameter(4, java.sql.Types.VARCHAR);
        cs.execute();
        ResultSet classes = (ResultSet)cs.getObject(2);
        while (classes.next()) {
          cla.add(classes.getString(1)+ "\t");
          cla.add(classes.getString(2)+ "\t");
          cla.add(Integer.toString(classes.getInt(3))+ "\t");
          cla.add(Integer.toString(classes.getInt(4))+ "\t");
          cla.add(Integer.toString(classes.getInt(5))+ "\t");
          cla.add(classes.getString(6)+ "\t");
          cla.add(Integer.toString(classes.getInt(7))+ "\t");
          cla.add(Integer.toString(classes.getInt(8)));
        }
        ResultSet students = (ResultSet)cs.getObject(3);
        while (students.next()) {
          Object[] obj = new Object[6];
          obj[0] = students.getString(1);
          obj[1] = students.getString(2);
          obj[2] = students.getString(3);
          obj[3] = students.getString(4);
          obj[4] = students.getDouble(5);
          obj[5] = students.getString(6);
          l.add(obj);
        }
        String err_message = cs.getString(4);
        if (err_message != null) {
          cla.add(err_message);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][6];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question 7 */
    //enroll a student into a class
    public static String enrollToClass(String B_no, String classid) {
      String query = "begin fun.enroll_std2cla(?,?,?); end;";
      CallableStatement cs = null;
      String message = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, B_no);
        cs.setString(2, classid);
        cs.registerOutParameter(3, java.sql.Types.VARCHAR);
        cs.execute();
        message = cs.getString(3);
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return message;
    }

    /** question 8 */
    //drop a student from a class
    public static String dropClass(String sid, String cid) {
      String query = "begin fun.drop_class(?,?,?); end;";
      CallableStatement cs = null;
      String message = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.setString(2, cid);
        cs.registerOutParameter(3, java.sql.Types.VARCHAR);
        cs.execute();
        message = cs.getString(3);
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return message;
    }

    /** question 9 */
    //delete a student from a class
    public static String deleteStudent(String sid) {
      String query = "begin fun.delete_student(?,?); end;";
      CallableStatement cs = null;
      String message = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.registerOutParameter(2, java.sql.Types.VARCHAR);
        cs.execute();
        message = cs.getString(2);
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return message;
    }


    public static void main(String[] args) {
      showStudents();System.out.println("");
      // showGrades();System.out.println("");
      // showLogs(); System.out.println("");
      // showGrades();System.out.println("");
      // showEnrollments();System.out.println("");
      // showCourseCredit();System.out.println("");
      // showCourses();System.out.println("");
      // showClasses();System.out.println("");
      // insertStudent("B009","Yang", "Liu", "graduate", 3.75, "yliu158@bu.edu");
      // getPrerequisite("CS", "532");System.out.println("");
      // getClassOfStudent("B001");System.out.println("");
      // getStudentsOfClass("c0007");
      // enrollToClass("B004", "c0007");
      // dropClass("B004", "c0007");
      // JdbcUtilities.deleteStudent("B009");
    }
}
