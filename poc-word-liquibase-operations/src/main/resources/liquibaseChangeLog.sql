-- liquibase formatted sql

-- changeset bhushan.kathar:1
CREATE TABLE "emp_data" ("id" INTEGER NOT NULL, "name" VARCHAR(50) NOT NULL, CONSTRAINT "emp_data_pkey" PRIMARY KEY ("id"));

-- changeset bhushan.kathar:2
CREATE TABLE "student" ("id" INTEGER NOT NULL, "name" VARCHAR(50) NOT NULL, CONSTRAINT "student_pkey" PRIMARY KEY ("id"));


--changeset bhushan.kathar:3
insert into student (id, name) values (1, 'bhushan');

--changeset bhushan.kathar:4
insert into student (id, name) values (2, 'Saama');

--changeset bhushan.kathar:5
ALTER TABLE student ADD Email varchar(255);

--changeset bhushan.kathar:6
insert into student (id, name) values (3, 'pharma');

