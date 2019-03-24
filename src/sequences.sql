CREATE SEQUENCE logs_seq START WITH 100;

CREATE OR REPLACE TRIGGER logs_bir 
BEFORE INSERT ON logs 
FOR EACH ROW

BEGIN
  SELECT logs_seq.NEXTVAL
  INTO   :new.log#
  FROM   dual;
END;
/