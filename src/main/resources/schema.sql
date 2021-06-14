CREATE SEQUENCE IF NOT EXISTS SEQ_CUST
    minvalue 1001
    maxvalue 99999999
    increment by 1
    start with 1001
    nocycle;
    
create table customer (
ID VARCHAR(20) PRIMARY KEY,
Name VARCHAR(20),
country varchar(20),
phone_number varchar(20),
VERSION NUMBER);

create table OWNER (
CUSTOMER_ID VARCHAR(20) ,
OWNER_Name VARCHAR(20),
OWNER_SSN varchar(20),
PRIMARY KEY(CUSTOMER_ID,OWNER_Name,OWNER_SSN));    
