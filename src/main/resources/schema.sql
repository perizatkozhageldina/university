DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;

CREATE TABLE course(id SERIAL PRIMARY KEY, name VARCHAR, level integer, hours integer);
CREATE TABLE groups(id SERIAL PRIMARY KEY, name VARCHAR);
CREATE TABLE lecture(id SERIAL PRIMARY KEY, name VARCHAR, number integer);
CREATE TABLE room(id SERIAL PRIMARY KEY, name VARCHAR, capacity integer);
CREATE TABLE student(id SERIAL PRIMARY KEY, name VARCHAR, surname VARCHAR, academicYear integer, groupId integer, CONSTRAINT fk_group FOREIGN KEY(groupId) REFERENCES groups(id));
CREATE TABLE teacher(id SERIAL PRIMARY KEY, name VARCHAR, surname VARCHAR, category varchar, hours integer);