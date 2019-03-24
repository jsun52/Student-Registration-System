# Student-Registration-System
use Oracle's PL/SQL and JDBC to create an application to support typical student registration tasks

The following tables from the Student Registration System will be used: 

	Students(B#, first_name, last_name, status, gpa, email, bdate, deptname)
      	TAs(B#, ta_level, office)
	Courses(dept_code, course#, title)
	Classes(classid, dept_code, course#, sect#, year, semester, limit, class_size, room, TA_B#)
        Enrollments(B#, classid, lgrade)
	Prerequisites(dept_code, course#, pre_dept_code, pre_course#)


In addition, the following table is also required:     

	Logs(log#, op_name, op_time, table_name, operation, key_value)
