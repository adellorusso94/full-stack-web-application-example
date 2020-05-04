# full-stack-web-application-example
 Front End in Angular, Back End in Spring Boot
 
 1. Start MongoDB Service with this command:
		mongod --auth --port 27017 --dbpath C:\data\db
 2. Launch Zipkin with this command (port 9411:
 		java -jar zipkin.jar
 3. Start Spring Boot web-services in this order:
 	1. ConfigServer (port 8888)
	2. GestUser (port 8019)
	3. Eureka (Service Discover, port 8761)
	4. AuthJWT (port 9100)
	5. Zuul (API Gateway, port 8765)
	6. Hystrix Dashboard (Circuit Breaker, port 9000) 
	7. Products, PriceArt & Promo, which contain:
		1. Ribbon (Load Balancer)
		2. Feign (Declarative REST Client)
 4. Start Angular application on Visual Studio Code.
 
 Credits: Nicola La Rocca ("Sviluppare Full Stack Applications con Spring Boot e Angular", "Creare Cloud Ready Apps con Spring Cloud, Docker e Kubernetes" - Corsi Udemy)
