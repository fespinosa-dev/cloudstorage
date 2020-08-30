CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    id INT PRIMARY KEY auto_increment,
    title VARCHAR(20),
    description VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    id INT PRIMARY KEY auto_increment,
    name VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    id INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);

INSERT INTO users (username,salt, password,firstname, lastname)
VALUES ('fespinosa','KgLW5H4EVh/OsKIwmRsIqA==', 'iQxEhDPhkPzmwEj8hk/r/A==', 'Fernando', 'Espinosa');

INSERT INTO notes (title, description, userId) VALUES ('Learning Spring Boot', 'This is a new note desc', 1);

INSERT INTO credentials (url, key, userId, username, password)
VALUES ('http://test.com', 'mpZsOgyNGRVL1i0lAEvFJw==', 1, 'username', 'Qc6c4UAc1X4zsrgWHcMp+Q==');