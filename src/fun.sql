create or replace package fun as
PROCEDURE show_STUDENTS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_TAS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_PREREQUISITES(c_res OUT SYS_REFCURSOR);
PROCEDURE show_LOGS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_ENROLLMENTS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_COURSES(c_res OUT SYS_REFCURSOR);
PROCEDURE show_CLASSES(c_res OUT SYS_REFCURSOR);


--used to insert a student into sql db
PROCEDURE insert_a_student (
  si IN students.B#%type,
  fna IN students.first_name%type,
  lna IN students.last_name%type,
  sts IN students.status%type,
  gp IN students.gpa%type,
  em IN students.email%type,
  bdt IN students.bdate%type,
  dpn IN students.deptname%type,
  res OUT SYS_REFCURSOR
);

--used to get the classes of a student
--get the TA of a class
PROCEDURE get_student_classes (
  classid_in IN classes.classid%type,
  res_classes OUT SYS_REFCURSOR,
  res_TAs OUT SYS_REFCURSOR,
  err OUT varchar2
);

--used to get all pre-req of all classes
PROCEDURE get_prerequisite (
  dc IN PREREQUISITES.dept_code%type,
  cn IN PREREQUISITES.course#%type,
  res_classes OUT SYS_REFCURSOR
);

--used to enroll a student into a class
PROCEDURE enroll_std2cla (
  B#_in IN students.B#%type,
  classid_in IN classes.CLASSID%type,
  err OUT varchar2
);

--used to check whether statisfied the pre-req requirment
PROCEDURE prerequisite_ok(
  B#_in IN students.B#%type,
  deptcode_in IN courses.dept_code%type,
  course#_in IN courses.course#%type,
  all_pre_ok OUT BOOLEAN
);


--used to drop a class from a student
PROCEDURE drop_class (
  B#_in IN students.B#%type,
  classid_in IN classes.CLASSID%type,
  mes OUT varchar2
);

--used to check whether may drop a class of a student
PROCEDURE ok_to_drop (
  B#_in IN students.B#%type,
  deptcode_in IN classes.dept_code%type,
  course#_in IN classes.course#%type,
  res OUT BOOLEAN
);


--used to delete a student
PROCEDURE delete_student (
  B#_in IN students.B#%type,
  mes OUT VARCHAR2
);

end;
/
show errors


CREATE OR REPLACE PACKAGE BODY fun
AS

  PROCEDURE show_STUDENTS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from STUDENTS;
  END;
  
  PROCEDURE show_TAS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from TAS;
  END;
  
  PROCEDURE show_PREREQUISITES (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from PREREQUISITES;
  END;

  PROCEDURE show_LOGS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from LOGS;
  END;

  PROCEDURE show_ENROLLMENTS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from ENROLLMENTS;
  END;

  PROCEDURE show_COURSES (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select DEPT_CODE, course#, TITLE from COURSES;
  END;

  PROCEDURE show_CLASSES (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from CLASSES;
  END;
  
  
  --used to insert a student into sql db
  PROCEDURE insert_a_student (
    si IN students.B#%type,
  fna IN students.first_name%type,
  lna IN students.last_name%type,
  sts IN students.status%type,
  gp IN students.gpa%type,
  em IN students.email%type,
  bdt IN students.bdate%type,
  dpn IN students.deptname%type,
  res OUT SYS_REFCURSOR)
  AS
  BEGIN
    insert into students (B#, first_name, last_name, status, gpa, email, bdate, deptname)
    values (si, fna, lna, sts, gp, em, bdt, dpn);
    open res for select * from students where B# = si;
  END;
  
  --question3: get the TA from a class
  --used to get all classes of a student
    PROCEDURE get_student_classes (
    classid_in IN classes.classid%type,
    res_classes OUT SYS_REFCURSOR,
    res_TAs OUT SYS_REFCURSOR,
    err OUT varchar2)
  AS
  classid char(5); dept_code varchar2(4); course# number(3); sect# number(2); year number(4); semester varchar2(8); limit number(3); class_size number(3); room varchar2(10); ta_B# char(4);
  B# char(4); ta_level varchar2(3); office varchar2(10); 
  BEGIN
    open res_classes for select * from classes where classid = classid_in;
    open res_TAs for select * from TAs where B# in (
      select TA_B# from classes where classid = classid_in
    );
    fetch res_classes into classid, dept_code, course#, sect#, year, semester, limit, class_size, room, ta_B#;
    if res_classes%notfound then
      err := 'The classid is invalid';
    end if;
    fetch res_TAs into B#, ta_level, office;
    if res_TAs%notfound then
      err := 'The class has no TA.';
    end if;
    open res_classes for select * from classes where classid = classid_in;
    open res_TAs for select * from TAs where B# in (
      select TA_B# from classes where classid = classid_in
    );
  END;
  
--used to get all the pre-req
  PROCEDURE get_prerequisite (
    dc IN PREREQUISITES.dept_code%type,
    cn IN PREREQUISITES.course#%type,
    res_classes OUT SYS_REFCURSOR
  )
  AS
  BEGIN
    open res_classes for select PRE_DEPT_CODE, PRE_COURSE# from PREREQUISITES
    start with dept_code = dc and course# = cn
    CONNECT BY PRIOR PRE_DEPT_CODE = DEPT_CODE and PRIOR PRE_COURSE# = COURSE#;
  END;  
  
--used to enroll a student into a class
  PROCEDURE enroll_std2cla (
    B#_in IN students.B#%type,
    classid_in IN classes.CLASSID%type,
    err OUT varchar2)
  AS
  cursor c_std is select * from students where students.B# = B#_in;
  a_std c_std%rowtype;
  cursor c_cla is select * from classes where classes.CLASSID = classid_in;
  a_cla c_cla%rowtype;
  cursor c_enr is select * from enrollments where enrollments.B# = B#_in and enrollments.CLASSID = classid_in;
  a_enr c_enr%rowtype;
  cursor c_allcla is select * from classes where CLASSID in (select CLASSID from enrollments where enrollments.B# = B#_in);
  a_allcla c_allcla%rowtype;
  count_cla PLS_INTEGER;
  ok_to_register BOOLEAN;
  BEGIN
  if not c_std%isopen then open c_std;
  end if;
  fetch c_std into a_std;
  if c_std%found then
    if not c_cla%isopen then open c_cla;
    end if;
    
    fetch c_cla into a_cla;
    if c_cla%found then
    
      if a_cla.limit <= a_cla.class_size then
        err := 'The class is already full.';
      else
        if not c_enr%isopen then open c_enr;
        end if;

        fetch c_enr into a_enr;
        if c_enr%found then
        err := 'The student is already in the class.';
        else
-- check if enrolled in more than four classes in the same semester of a year
          if not c_allcla%isopen then open c_allcla;
          end if;
          fetch c_allcla into a_allcla;
          count_cla := 0;
          while c_allcla%found loop
            if a_cla.semester = a_allcla.semester and a_cla.year = a_allcla.year then
            count_cla := count_cla +1;
            end if;
            fetch c_allcla into a_allcla;
          end loop;
          close c_allcla;


          if count_cla >= 5 then
            err := 'Students cannot be enrolled in more than five classes in the same semester.';
          elsif count_cla = 4 then
            err := 'The students will be overloaded with the new enrollment.';
          
          else
            ok_to_register := TRUE;
            prerequisite_ok(B#_in, a_cla.dept_code, a_cla.course#, ok_to_register);
            if ok_to_register then
              insert into enrollments values (B#_in, classid_in, null);
              err := 'Done with enrolling.';
            else
              err := 'Prerequisite not satified.';
            end if;

          end if;
        end if;
        close c_enr;
      end if;
    else err := 'The classid is invalid.';
    end if;
    close c_cla;
  else err := 'The B# is invalid.';
  end if;
  close c_std;
  END;
--used to check whether statisfies the pre-req requirment
  PROCEDURE prerequisite_ok(
    B#_in IN students.B#%type,
    deptcode_in IN courses.dept_code%type,
    course#_in IN courses.course#%type,
    all_pre_ok OUT BOOLEAN)
  AS
  cursor all_pre is select pre_dept_code, pre_course# from PREREQUISITES
  start with dept_code = deptcode_in and course# = course#_in
  CONNECT BY PRIOR pre_dept_code = dept_code and PRIOR pre_course# = course#;
  one_pre all_pre%rowtype;

  cursor all_taken is select * from
  classes join (select * from enrollments where B# = B#_in) e on classes.CLASSID = e.CLASSID;
  one_taken all_taken%rowtype;

  found BOOLEAN;
  BEGIN
    all_pre_ok := TRUE;
    open all_pre;
    fetch all_pre into one_pre;
    while all_pre%found loop
      open all_taken;
      found := FALSE;
      fetch all_taken into one_taken;

      while all_taken%found loop
        if one_taken.dept_code = one_pre.pre_dept_code and one_taken.course# = one_pre.pre_course# and
          (one_taken.LGRADE = 'A' OR one_taken.LGRADE = 'B' OR one_taken.LGRADE = 'C') then
           found := TRUE;
        end if;
        fetch all_taken into one_taken;
      end loop;

      if not found then
        all_pre_ok := FALSE; EXIT;
      end if;
      close all_taken;
      fetch all_pre into one_pre;
    end loop;
    close all_pre;
  END;

--used to drop a class from a student
  PROCEDURE drop_class (
    B#_in IN students.B#%type,
    classid_in IN classes.CLASSID%type,
    mes OUT varchar2)
  AS
  cursor c_std is select B# from students where B# = B#_in;
  cursor c_cla is select * from classes where CLASSID = classid_in;
  a_std c_std%rowtype;
  a_cla c_cla%rowtype;
  cursor c_enr is select * from enrollments where B# = B#_in and CLASSID = classid_in;
  a_enr c_enr%rowtype;
  cursor allothercla is select * from enrollments where B# = B#_in;
  anothercla allothercla%rowtype;
  cursor allotherstd is select * from enrollments where classid = classid_in;
  anotherstd allotherstd%rowtype;
  drop_ok BOOLEAN;
  BEGIN
    open c_std;
    open c_cla;
    open c_enr;
    fetch c_std into a_std;
    fetch c_cla into a_cla;
    fetch c_enr into a_enr;
    if not c_std%found then
      mes := 'The B# is invalid.';
    else
      if not c_cla%found then
        mes := 'The classid is invalid.';
      else
        if not c_enr%found then
           mes := 'The student is not enrolled in the class';
        else
          drop_ok := TRUE;
          ok_to_drop(B#_in, a_cla.dept_code, a_cla.course#, drop_ok);
          if drop_ok then
            -- drop the class
            delete from enrollments where CLASSID = classid_in and B# = B#_in;
            open allothercla;
            fetch allothercla into anothercla;
            open allotherstd;
            fetch allotherstd into anotherstd;
            if allothercla%notfound then
              if allotherstd%notfound then
                mes := 'The student is not enrolled in any class.\n The class now has no student';
              else
                mes := 'The student is not enrolled in any class.';
              end if;
            else
              if allotherstd%notfound then
                mes := 'The class now has no student';
              else
                mes := 'Done with dropping.';
              end if;
            end if;
            close allothercla;
            close allotherstd;
          else
            mes:= 'The drop is not permitted because another class the student registered uses it as a prerequisite.';
          end if;
        end if;
      end if;
    end if;
    close c_std;
    close c_cla;
    close c_enr;
  END;

 
--used to check whether may drop a class from a student
  PROCEDURE ok_to_drop (
    B#_in IN students.B#%type,
    deptcode_in IN classes.dept_code%type,
    course#_in IN classes.course#%type,
    res OUT BOOLEAN)
  AS
  cursor all_pos is select dept_code, course# from PREREQUISITES
  start with pre_dept_code = deptcode_in and pre_course# = course#_in
  connect by PRIOR dept_code = pre_dept_code and PRIOR course# = pre_course#;
  one_pos all_pos%rowtype;

  cursor all_taken is select dept_code, course# from
  classes where CLASSID in (select CLASSID from enrollments where B# = B#_in);
  one_taken all_taken%rowtype;

  found BOOLEAN;
  BEGIN
    res := TRUE;
    open all_pos;
    fetch all_pos into one_pos;
    while all_pos%found loop
        open all_taken;
        fetch all_taken into one_taken;
        while all_taken%found loop
          if one_taken.dept_code = one_pos.dept_code and one_taken.course# = one_pos.course# then
            res := FALSE; EXIT;
          end if;
          fetch all_taken into one_taken;
        end loop;
        close all_taken;
      fetch all_pos into one_pos;
    end loop;
    close all_pos;
  END;

--used to delete a student from sql db
  PROCEDURE delete_student (
    B#_in IN students.B#%type,
    mes OUT VARCHAR2)
  AS
  cursor c_std is select * from students where B# = B#_in;
  a_std c_std%rowtype;
  BEGIN
    open c_std;
    fetch c_std into a_std;
    if c_std%found then
      delete from students where B# = B#_in;
      mes := 'Done with deletion.';
    else
      mes := 'The B# is invalid.';
    end if;
    close c_std;
  END;
	
	
end;
/
show errors