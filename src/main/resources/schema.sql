CREATE TABLE USERS (
    ID INT AUTO_INCREMENT ,
    UUID RAW(16),
    NAME VARCHAR(100) ,
    EMAIL VARCHAR(100) UNIQUE ,
    PASSWORD VARCHAR(100),
    TOKEN VARCHAR(255),
    IS_ACTIVE BOOLEAN,
    CREATED TIMESTAMP(6),
    MODIFIED TIMESTAMP(6),
    LAST_LOGIN  TIMESTAMP(6),
    CONSTRAINT pk_id_user PRIMARY KEY (ID)
);

CREATE TABLE PHONE (
    ID INT AUTO_INCREMENT ,
    NUMBER VARCHAR(20),
    CITY_CODE VARCHAR(10),
    COUNTRY_CODE VARCHAR(10),
    USER_ID INT,
    CONSTRAINT pk_id_phone PRIMARY KEY (ID),
    CONSTRAINT fk_user_id FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
