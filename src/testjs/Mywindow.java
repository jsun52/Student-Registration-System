//importing libraries
package testjs;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.Date;

import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;
//declare the window frame and buttons
public class Mywindow {

  private static JFrame frame;
  private static JPanel main_panel;
  private static JPanel showStudents_panel;
  private static String[] show_button_names = {
    "Students Table",
    "TAs Table",
    "Classes Table",
    "Courses Table",
    "Enrollments Table",
    "Prerequisites Table",
    "Logs Table"
  };

  private static String[] action_button_names = {
    "AUTHOR: JS & KL",
    "TA From a Class",
    "Prerequisite",
    "StudentsOfClass",
    "ENROLL",
    "DROP",
    "DeleteStudent"
  };

  public Mywindow() {
    __init__();
  }
//initial the window frame
  private void __init__() {
    frame = new JFrame();
    frame.setTitle("Course Selection System......");
    frame.setBounds(150, 100, 955, 627);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new CardLayout(20, 0));
    main_panel = new JPanel();
    frame.getContentPane().add(main_panel, "main");
    main_panel.setLayout(null);
    createShowButtons();
    createActButtons();
    frame.setVisible(true);
  }


//get the data from sql db
  private Object[][] getData(int index) {
    Object[][] res = null;
    switch (index) {
      case 0: res = JdbcUtilities.showStudents(); break;
      case 1: res = JdbcUtilities.showTAs(); break;
      case 2: res = JdbcUtilities.showClasses(); break;
      case 3: res = JdbcUtilities.showCourses(); break;
      case 4: res = JdbcUtilities.showEnrollments(); break;
      case 5: res = JdbcUtilities.showPrerequisites(); break;
      case 6: res = JdbcUtilities.showLogs(); break;
    }
    return res;
  }
//set all the columns in the show window
  private String[] getColumns(int index) {
    String[] rv = null;
    switch (index) {
      case 0: String[] res0 = {"B#", "first_name", "last_name", "status", "gpa", "email", "bdate", "deptname"}; rv = res0; break;
      case 1: String[] res1 = {"B#", "ta_level", "office"}; rv = res1; break;
      case 2: String[] res2 = {"classid", "dept_code", "course#", "sect#", "year", "semester", "limit", "class_size", "room", "TA_B#"}; rv = res2; break;
      case 3: String[] res3 = {"dept_code", "course#", "title"}; rv = res3; break;
      case 4: String[] res4 = {"B#", "classid", "lgrade"}; rv = res4; break;
      case 5: String[] res5 = {"dept_code", "course#", "pre_dept_code", "pre_course#"}; rv = res5; break;
      case 6: String[] res6 = {"log#","op_name","op_time","table_name","operation","key_value"}; rv = res6; break;
    }
    return rv;
  }

//create all the show_button in the window frame
  private void createShowButtons() {
    for (int i = 0; i < show_button_names.length; ++i) {
      Object[][] data = getData(i);
      String[] columns = getColumns(i);
      createShowButton(data, columns, i);
    }
  }
//create all the show_button in the window frame
  private void createShowButton(Object[][] data, String[] columns, final int index) {
    JButton b = new JButton(show_button_names[index]);
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[][] data = getData(index);
        String[] columns = getColumns(index);
        TableGenerator  g = new TableGenerator();
        JFrame f = g.initTable(data, columns);
        f.setVisible(true);//set it to visible
      }
    });
    b.setFont(new Font("Dialog", Font.BOLD, 17));
    b.setBounds(56, 33+index*70, 200, 66);
    main_panel.add(b);// add this button in the main window
  }
//create act_buttons
  private void createActButtons() {
    for (int i = 0; i < action_button_names.length; ++i) {
      createActButton(i);
    }
  }
//create act_buttons
  private void createActButton (final int index) {
    JButton b = new JButton(action_button_names[index]);
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switch (index) {
          //case 0: insertStudentInputWindow(); break;
          case 1: getClassOfStudentInputWindow();break;
          case 2: getPrerequisiteInputWindow();break;
          case 3: getStudentsOfClassInputWindow();break;
          case 4: enrollToClassInputWindow();break;
          case 5: dropClassInputWindow(); break;
          case 6: deleteStudentInputWindow();
        }
      }
    });
    b.setFont(new Font("Dialog", Font.BOLD, 17));
    b.setBounds(600, 33+index*70, 240, 56);
    main_panel.add(b);
  }
//student input window, setting up all input entries and sends the user input
  /*private void insertStudentInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 800, 230);
    JPanel p = new JPanel();
    final JTextField B_no = new JTextField();
    final JTextField first_name = new JTextField();
    final JTextField last_name = new JTextField();
    final JTextField status = new JTextField();
    final JTextField gpa = new JTextField();
    final JTextField email = new JTextField();
    final JTextField bdate = new JTextField();
    final JTextField deptname = new JTextField();
    JLabel B_no_l = new JLabel("B#");
    JLabel first_name_l = new JLabel("first_name");
    JLabel last_name_l = new JLabel("last_name");
    JLabel status_l = new JLabel("status");
    JLabel gpa_l = new JLabel("gpa");
    JLabel email_l = new JLabel("email");
    JLabel bdate_l = new JLabel("bdate");
    JLabel deptname_l = new JLabel("d");
    B_no_l.setBounds(10, 11, 90, 20);
    B_no.setBounds(105, 11, 233, 20);
    first_name_l.setBounds(10, 42, 90, 20);
    first_name.setBounds(105, 42, 233, 20);
    last_name_l.setBounds(10, 73, 90, 20);
    last_name.setBounds(105, 73, 233, 20);
    status_l.setBounds(410, 11, 90, 20);
    status.setBounds(505, 11, 233, 20);
    gpa_l.setBounds(410, 42, 90, 20);
    gpa.setBounds(505, 42, 233, 20);
    email_l.setBounds(410, 73, 90, 20);
    email.setBounds(505, 73, 233, 20);
    bdate_l.setBounds(410, 73, 90, 20);//zuobiao yao gai
    bdate.setBounds(505, 73, 233, 20);
    deptname_l.setBounds(410, 73, 90, 20);//zuobiao yao gai
    deptname.setBounds(505, 73, 233, 20);
    p.setLayout(null);
    p.add(B_no_l);
    p.add(B_no);
    p.add(first_name_l);
    p.add(first_name);
    p.add(last_name_l);
    p.add(last_name);
    p.add(status_l);
    p.add(status);
    p.add(gpa_l);
    p.add(gpa);
    p.add(email_l);
    p.add(email);
    p.add(bdate_l);
    p.add(bdate);
    p.add(deptname_l);
    p.add(deptname);

    final JTextArea sinfo = new JTextArea(2, 100);
    sinfo.setEditable(false);
    sinfo.setBounds(10, 150, 750, 30);
    sinfo.setLineWrap(true);
    p.add(sinfo);

    JButton act = new JButton("Insert");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.insertStudent(B_no.getText(), first_name.getText(),
        last_name.getText(), status.getText(), Double.parseDouble(gpa.getText()), email.getText(), bdate.getText() , deptname.getText());
        sinfo.setText(message);
      }
    });
    act.setBounds(500, 100, 150, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }*/
  
  
//get the TA from class
  private void getClassOfStudentInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 500);
    JPanel p = new JPanel();
    p.setLayout(null);
    final JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("classid");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);
    final String[] columns = {"classid", "dept_code", "course#", "sect#", "year", "semester", "limit", "class_size", "room", "TA_B#"};
    final JTable table = new JTable(new Object[0][10], columns);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setBounds(10, 130, 880, 350);
    final JTextArea sinfo = new JTextArea(2, 100);
    sinfo.setEditable(false);
    sinfo.setBounds(10, 70, 880, 50);
    sinfo.setLineWrap(true);
    p.add(table);
    p.add(sinfo);
    JButton act = new JButton("Check Out");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ArrayList<String> message = new ArrayList<String>();
        Object[][] data = JdbcUtilities.getClassOfStudent(sid.getText(), message);
        DefaultTableModel dataModel = new DefaultTableModel(data, columns);
        table.setModel(dataModel);
        String mes = "";
        for (int k = 0; k < 6; ++k) {
          mes += message.get(k);
        }
        if (message.size() == 7) mes += "\n " + message.get(6);
        sinfo.setText(mes);
      }
    });
    act.setBounds(399, 11, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }
  
  
//show all the pre-req of all the courses
  private void getPrerequisiteInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 500);
    JPanel p = new JPanel();
    p.setLayout(null);
    final JTextField dept_code = new JTextField();
    final JTextField course_no = new JTextField();
    JLabel dept_code_l = new JLabel("dept_code");
    JLabel course_no_l = new JLabel("course#");
    dept_code_l.setBounds(10, 11, 90, 30);
    dept_code.setBounds(105, 11, 233, 30);
    course_no_l.setBounds(10, 43, 90, 30);
    course_no.setBounds(105, 43, 233, 20);
    p.add(dept_code);
    p.add(dept_code_l);
    p.add(course_no);
    p.add(course_no_l);

    final String[] columns = {"pre_dept_code", "pre_course#"};
    final JTable table = new JTable(new Object[0][2], columns);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setBounds(10, 120, 880, 200);
    p.add(table);

    JButton act = new JButton("Check Out");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[][] data = JdbcUtilities.getPrerequisite(dept_code.getText(), course_no.getText());
        DefaultTableModel dataModel = new DefaultTableModel(data, columns);
        table.setModel(dataModel);
      }
    });
    act.setBounds(399, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }
//show all the students who are taking one class
  private void getStudentsOfClassInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 620);
    JPanel p = new JPanel();
    p.setLayout(null);
    final JTextField cid = new JTextField();
    JLabel cid_l = new JLabel("CID");
    cid_l.setBounds(10, 11, 90, 30);
    cid.setBounds(105, 11, 233, 30);
    p.add(cid_l);
    p.add(cid);
    final String[] columns = {"SID", "FIRSTNAME", "LASTNAME", "STATUS", "GPA", "EMAIL"};
    final JTable table = new JTable(new Object[0][6], columns);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setBounds(10, 120, 880, 460);
    p.add(table);
    final JTextArea cinfo = new JTextArea(2, 100);
    cinfo.setEditable(false);
    cinfo.setBounds(10, 60, 880, 50);
    cinfo.setLineWrap(true);
    p.add(cinfo);

    JButton act = new JButton("Check Out");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ArrayList<String> message = new ArrayList<String>();
        Object[][] data = JdbcUtilities.getStudentsOfClass(cid.getText(), message);
        DefaultTableModel dataModel = new DefaultTableModel(data, columns);
        table.setModel(dataModel);
        String mes = "";
        if (message.size() == 1) mes = message.get(0);
        if (message.size() == 9) {
        	for (int k = 0; k < 8; ++k) mes += message.get(k);
        	mes += "\n" + message.get(8);
        }
        if (message.size() == 8)for (int k = 0; k < 8; ++k) mes += message.get(k);
        cinfo.setText(mes);
      }
    });
    act.setBounds(500, 11, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }

    //used to enroll a student into a class
  private void enrollToClassInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 260);
    JPanel p = new JPanel();
    p.setLayout(null);
    final JTextField classid = new JTextField();
    JLabel classid_l = new JLabel("classid");
    classid_l.setBounds(10, 43, 90, 30);
    classid.setBounds(105, 43, 233, 30);
    p.add(classid_l);
    p.add(classid);
    final JTextField B_no = new JTextField();
    JLabel B_no_l = new JLabel("B#");
    B_no_l.setBounds(10, 11, 90, 30);
    B_no.setBounds(105, 11, 233, 30);
    p.add(B_no_l);
    p.add(B_no);

    final JTextArea info = new JTextArea(2, 100);
    info.setEditable(false);
    info.setBounds(10, 90, 880, 30);
    info.setLineWrap(true);
    p.add(info);

    JButton act = new JButton("Enroll");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.enrollToClass(B_no.getText(), classid.getText());
        info.setText(message);
      }
    });
    act.setBounds(500, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }
//used to drop one class from a student
  private void dropClassInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 260);
    JPanel p = new JPanel();
    p.setLayout(null);
    final JTextField cid = new JTextField();
    JLabel cid_l = new JLabel("classid");
    cid_l.setBounds(10, 43, 90, 30);
    cid.setBounds(105, 43, 233, 30);
    p.add(cid_l);
    p.add(cid);
    final JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("B#");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);

    final JTextArea info = new JTextArea(2, 100);
    info.setEditable(false);
    info.setBounds(10, 90, 880, 30);
    info.setLineWrap(true);
    p.add(info);

    JButton act = new JButton("Drop");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.dropClass(sid.getText(), cid.getText());
        info.setText(message);
      }
    });
    act.setBounds(500, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }
//used to delete one student in the student system
  private void deleteStudentInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 260);
    JPanel p = new JPanel();
    p.setLayout(null);
    final JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("B#");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);

    final JTextArea info = new JTextArea(2, 100);
    info.setEditable(false);
    info.setBounds(10, 90, 880, 30);
    info.setLineWrap(true);
    p.add(info);

    JButton act = new JButton("Delete");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.deleteStudent(sid.getText());
        info.setText(message);
      }
    });
    act.setBounds(500, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Mywindow w = new Mywindow();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
//used to generate tables we need
  public class TableGenerator extends JFrame {
    JFrame f;
    JTable table;
    public JFrame initTable(Object[][] data, String[] columns) {
      f = new JFrame();
      f.setBounds(400, 200, 900, 600);
      table = new JTable(data, columns);
      table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      f.add(new JScrollPane(table));
      f.setVisible(true);
      return f;
    }
  };

}
