###########################################################################################################################################################################################################################################################################################
-- DROP ALL EXISTING TABLES

DROP TABLE if EXISTS credit
DROP TABLE if EXISTS user_role
DROP TABLE if EXISTS user
DROP TABLE if EXISTS role
###########################################################################################################################################################################################################################################################################################
-- CREATE ALL TABLES

CREATE TABLE credit (credi_id bigint NOT NULL auto_increment, commision_fee INTEGER NOT NULL, credit_margin DOUBLE precision NOT NULL, mortgage_debt INTEGER NOT NULL, name VARCHAR(255) NOT NULL, mortgage_term INTEGER NOT NULL, wibor DOUBLE precision NOT NULL, PRIMARY KEY (credi_id))
CREATE TABLE role (role_id INTEGER NOT NULL auto_increment, role VARCHAR(255), PRIMARY KEY (role_id))
CREATE TABLE user (user_id INTEGER NOT NULL auto_increment, active INTEGER, email VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, PRIMARY KEY (user_id))
CREATE TABLE user_role (user_id INTEGER NOT NULL, role_id INTEGER NOT NULL, PRIMARY KEY (user_id, role_id))
###########################################################################################################################################################################################################################################################################################
-- POPULATE DATABASE

INSERT INTO role VALUES (1,'ADMIN')
###########################################################################################################################################################################################################################################################################################
INSERT INTO user (active, email, last_name, name, password) VALUES (1, 'test@test.pl', 'test', 'test', '$2a$10$xnFGF7XYfd0J.F9J/5qFfOwqSPLC6B4YLcmZVceOF44GESTnR7VY6')
###########################################################################################################################################################################################################################################################################################
INSERT INTO user_role (user_id, role_id) VALUES (1, 1)
###########################################################################################################################################################################################################################################################################################
