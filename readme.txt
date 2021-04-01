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


The following api calls are created:

1) get the camp site availbilities (A HTTP.GET method call)
   http://localhost:8080/campsite/reservation/availibility
	
2) make reservation (A HTTP.POST method call)
   http://localhost:8080/campsite/reservation
   
   With a request body as below JSON
   
   {
	  "first_name" : "Nicky",
	  "last_name" : "Siu",
	  "email" : "nic1123@gmail.com",
	  "arrival_date" : "2021-04-23",
	  "departure_date" : "2021-04-26"
	  
	}
	
         
3) modify reservation (A HTTP.PUT method call)
   http://localhost:8080/campsite/reservation/{reservationId}
   
   With a request body as below JSON
   
   {
	  "first_name" : "Nicky",
	  "last_name" : "Siu",
	  "email" : "nic1123@gmail.com",
	  "arrival_date" : "2021-04-23",
	  "departure_date" : "2021-04-26"
	  
	}
	
4) delete reservation (A HTTP.DELETE method call)
   http://localhost:8080/campsite/reservation/{reservationId}

