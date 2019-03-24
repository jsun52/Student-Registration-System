create or replace trigger tg_check_after_insert_enroll
after insert 
	on enrollments
	for each row
declare
	satisfied boolean;
begin
	for cursor1 in (
		select * from classes c
		where c.classid = :new.classid)
	loop
		if (cursor1.limit >= cursor1.class_size) then
			update classes set class_size = class_size + 1
			where classid = cursor1.classid;
			dbms_output.put_line(chr(10) || 'class ' || cursor1.classid || ' size increases.' || chr(10));
		end if;
	end loop;
end;
/

create or replace trigger tg_check_after_delete_enroll
after delete 
	on enrollments
	for each row
declare
	satisfied boolean;
begin
	for cursor1 in (
		select * from classes c
		where c.classid = :old.classid)
	loop
		if (cursor1.class_size > 0) then
			update classes set class_size = class_size - 1
			where classid = cursor1.classid;
			dbms_output.put_line(chr(10) || 'class ' || cursor1.classid || ' size decrease.' || chr(10));
		end if;
	end loop;
end;
/

create or replace trigger tg_check_before_delete_student
before delete 
	on students
	for each row
declare
	satisfied boolean;
begin
	/* delete student in tas */
	for cursor1 in (
		select * from tas t
		where t.b# = :old.b#)
	loop
		delete from tas t 
		where t.b# = :old.b#;
		dbms_output.put_line(chr(10) || cursor1.b# || ' deleted in TAs table' || chr(10));
	end loop;

	/* delete student in enrollment */
	for cursor2 in (
		select * from enrollments e
		where e.b# = :old.b#)
	loop
		delete from enrollments e
		where e.b# = :old.b#;
		dbms_output.put_line(chr(10) || cursor2.b# || ' deleted in enrollments table' || chr(10));
	end loop;
end;
/

create or replace trigger tg_log_after_delete_student
after delete 
	on students
	for each row
declare
	satisfied boolean;
begin
	insert into logs(op_name,op_time,table_name,operation,key_value) 
	values(user,sysdate,'students','delete',:old.b#);
end;
/

create or replace trigger tg_log_after_insert_enrollment
after insert 
	on enrollments
	for each row
declare
	satisfied boolean;
begin
	insert into logs(op_name,op_time,table_name,operation,key_value) 
	values(user,sysdate,'enrollments','insert',:new.b# || ',' ||:new.classid);
end;
/

create or replace trigger tg_log_after_delete_enrollment
after delete 
	on enrollments
	for each row
declare
	satisfied boolean;
begin
	insert into logs(op_name,op_time,table_name,operation,key_value) 
	values(user,sysdate,'enrollments','delete',:old.b# || ',' ||:old.classid);
end;
/
show errors

