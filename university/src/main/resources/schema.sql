DROP TABLE IF EXISTS student_groups;
DROP TABLE IF EXISTS course_lecture;
DROP TABLE IF EXISTS course_groups;
DROP TABLE IF EXISTS specialization_groups;
DROP TABLE IF EXISTS specialization_department;
DROP TABLE IF EXISTS teacher_department;
DROP TABLE IF EXISTS school_department;
DROP TABLE IF EXISTS audience;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS school;
DROP TABLE IF EXISTS specialization;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;

CREATE TABLE audience(audience_id SERIAL PRIMARY KEY, room integer, capacity integer);
CREATE TABLE course(course_id SERIAL PRIMARY KEY, name VARCHAR(50), description VARCHAR(250), creditHours integer, teacher_id integer, groups_id integer);
CREATE TABLE department(department_id SERIAL PRIMARY KEY, name VARCHAR(50), school_id integer);
CREATE TABLE groups(group_id SERIAL PRIMARY KEY, name VARCHAR(50), specialization_id integer);
CREATE TABLE lecture(lecture_id SERIAL PRIMARY KEY, theme VARCHAR(50), startTime TIMESTAMP, endTime TIMESTAMP, audience_id integer, course_id integer);
CREATE TABLE school(school_id SERIAL PRIMARY KEY, name VARCHAR(50));
CREATE TABLE specialization(specialization_id SERIAL PRIMARY KEY, name VARCHAR(50), department_id integer);
CREATE TABLE student(student_id SERIAL PRIMARY KEY, person_id integer, groups_id integer);
CREATE TABLE teacher(teacher_id SERIAL PRIMARY KEY, salary integer, person_id integer, department_id integer);
CREATE TABLE student_groups (student_id INTEGER REFERENCES student (student_id) ON UPDATE CASCADE ON DELETE CASCADE, 
group_id INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT student_groups_pkey PRIMARY KEY (student_id, group_id));
CREATE TABLE course_lecture (course_id INTEGER REFERENCES course (course_id) ON UPDATE CASCADE ON DELETE CASCADE, 
lecture_id INTEGER REFERENCES lecture (lecture_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT course_lecture_pkey PRIMARY KEY (course_id, lecture_id));
CREATE TABLE course_groups (course_id INTEGER REFERENCES course (course_id) ON UPDATE CASCADE ON DELETE CASCADE, 
group_id INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT course_groups_pkey PRIMARY KEY (course_id, group_id));
CREATE TABLE specialization_groups (specialization_id INTEGER REFERENCES specialization (specialization_id) ON UPDATE CASCADE ON DELETE CASCADE, 
group_id INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT specialization_groups_pkey PRIMARY KEY (specialization_id, group_id));
CREATE TABLE specialization_department (specialization_id INTEGER REFERENCES specialization (specialization_id) ON UPDATE CASCADE ON DELETE CASCADE, 
department_id INTEGER REFERENCES department (department_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT specialization_department_pkey PRIMARY KEY (specialization_id, department_id));
CREATE TABLE teacher_department (teacher_id INTEGER REFERENCES teacher (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE, 
department_id INTEGER REFERENCES department (department_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT teacher_department_pkey PRIMARY KEY (teacher_id, department_id));
CREATE TABLE school_department (school_id INTEGER REFERENCES school (school_id) ON UPDATE CASCADE ON DELETE CASCADE, 
department_id INTEGER REFERENCES department (department_id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT school_department_pkey PRIMARY KEY (school_id, department_id));