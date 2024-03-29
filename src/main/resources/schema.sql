DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;

CREATE TABLE course(id BIGSERIAL PRIMARY KEY, name VARCHAR, level INTEGER, hours INTEGER);
CREATE TABLE groups(id BIGSERIAL PRIMARY KEY, name VARCHAR, maxStudents INTEGER);
CREATE TABLE lecture(id BIGSERIAL PRIMARY KEY, name VARCHAR, number INTEGER);
CREATE TABLE room(id BIGSERIAL PRIMARY KEY, name VARCHAR, capacity INTEGER);
CREATE TABLE student(id BIGSERIAL PRIMARY KEY, name VARCHAR, surname VARCHAR, academicYear INTEGER, groupId BIGINT, CONSTRAINT fk_group FOREIGN KEY(groupId) REFERENCES groups(id));
CREATE TABLE teacher(id BIGSERIAL PRIMARY KEY, name VARCHAR, surname VARCHAR, category VARCHAR, hours INTEGER);