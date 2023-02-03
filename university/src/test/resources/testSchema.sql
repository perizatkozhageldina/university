DROP TABLE IF EXISTS course CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS lecture CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS teacher CASCADE;

CREATE TABLE course(courseId SERIAL PRIMARY KEY, level integer, hours integer);
CREATE TABLE groups(groupId SERIAL PRIMARY KEY, studentCount integer);
CREATE TABLE lecture(lectureId SERIAL PRIMARY KEY, number integer);
CREATE TABLE room(roomId SERIAL PRIMARY KEY, capacity integer);
CREATE TABLE student(studentId SERIAL PRIMARY KEY, academicYear integer, groupId integer, CONSTRAINT fk_group FOREIGN KEY(groupId) REFERENCES groups(groupId));
CREATE TABLE teacher(teacherId SERIAL PRIMARY KEY, category varchar, hours integer);

insert into groups values(1, 20);
insert into groups values(3, 20);
insert into groups values(5, 20);