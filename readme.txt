The application is divided into 3 layers:

1) Controller - this layer will interface with all HTTP calls
2) Service layer - this layer contains all business logic
3) repositoy - this layer will make all the calls to the underlying DB

H2 database is used in this project. One table is recreated with the following schema:

CREATE TABLE reservation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  arrival_date DATE NOT NULL,
  departure_date DATE NOT NULL
);