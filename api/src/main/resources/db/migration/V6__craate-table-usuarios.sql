CREATE TABLE usuarios (
     id BIGINT NOT NULL AUTO_INCREMENT,
     username VARCHAR(50) NOT NULL,
     password VARCHAR(200) NOT NULL,

     PRIMARY KEY (id)
);