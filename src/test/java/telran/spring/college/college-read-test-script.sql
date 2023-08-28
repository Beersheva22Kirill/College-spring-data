delete from marks where 1 = 1;
delete from subjects where 1 = 1;
delete from students_lecturers where 1 = 1;

insert into students_lecturers (id, name, birth_date, dtype) values(123,'Yuri','2001-12-12','Student');
insert into students_lecturers (id, name, birth_date, dtype) values(124,'Kirill','2001-04-16','Student');
insert into students_lecturers (id, name, birth_date, dtype) values(125,'Andrey','2001-09-13','Student');
insert into students_lecturers (id, name, birth_date, dtype) values(126,'Dmitri','2001-03-09','Student');
insert into students_lecturers (id, name, birth_date, dtype) values(127,'David','2001-02-18','Student');
insert into students_lecturers (id, name, birth_date, dtype) values(128,'Neuch','2001-02-18','Student');

insert into students_lecturers (id, name, birth_date, dtype) values(321,'Igor Korol','1984-02-18','Lecturer');
insert into students_lecturers (id, name, birth_date, dtype) values(322,'Andrey Sobol','1982-04-18','Lecturer');
insert into students_lecturers (id, name, birth_date, dtype) values(323,'Yana Polay','1986-03-14','Lecturer');

insert into subjects (id, name, hours, lecturer_id, subject_type) values('S1','Java Core', 100, 321, 'BACK_END');
insert into subjects (id, name, hours, lecturer_id, subject_type) values('S2','Java Technologies', 100, 321, 'BACK_END');
insert into subjects (id, name, hours, lecturer_id, subject_type) values('S3','Java Script', 100, 322, 'FRONT_END');
insert into subjects (id, name, hours, lecturer_id, subject_type) values('S4','React', 100, 323, 'FRONT_END');

insert into marks (student_id, subject_id, mark) values(123,'S1', 80);
insert into marks (student_id, subject_id, mark) values(123,'S2', 90);

insert into marks (student_id, subject_id, mark) values(124,'S1', 100);
insert into marks (student_id, subject_id, mark) values(124,'S2', 90);
insert into marks (student_id, subject_id, mark) values(124,'S3', 90);
insert into marks (student_id, subject_id, mark) values(124,'S4', 100);

insert into marks (student_id, subject_id, mark) values(125,'S1', 87);
insert into marks (student_id, subject_id, mark) values(125,'S2', 70);
insert into marks (student_id, subject_id, mark) values(125,'S3', 70);

insert into marks (student_id, subject_id, mark) values(126,'S1', 90);
insert into marks (student_id, subject_id, mark) values(126,'S2', 90);
insert into marks (student_id, subject_id, mark) values(126,'S3', 80);
insert into marks (student_id, subject_id, mark) values(126,'S4', 90);

insert into marks (student_id, subject_id, mark) values(127,'S1', 86);
insert into marks (student_id, subject_id, mark) values(127,'S2', 87);
insert into marks (student_id, subject_id, mark) values(127,'S3', 87);
insert into marks (student_id, subject_id, mark) values(127,'S4', 87);
