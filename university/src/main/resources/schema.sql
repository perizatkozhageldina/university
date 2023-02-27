DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;

CREATE TABLE course(id SERIAL PRIMARY KEY, level integer, hours integer);
CREATE TABLE groups(id SERIAL PRIMARY KEY, name VARCHAR);
CREATE TABLE lecture(id SERIAL PRIMARY KEY, number integer);
CREATE TABLE room(id SERIAL PRIMARY KEY, capacity integer);
CREATE TABLE student(id SERIAL PRIMARY KEY, academicYear integer, groupId integer, CONSTRAINT fk_group FOREIGN KEY(id) REFERENCES groups(id));
CREATE TABLE teacher(id SERIAL PRIMARY KEY, category varchar, hours integer);