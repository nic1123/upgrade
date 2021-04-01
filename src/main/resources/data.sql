
DROP TABLE IF EXISTS reservation;


CREATE TABLE reservation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  arrival_date DATE NOT NULL,
  departure_date DATE NOT NULL
);