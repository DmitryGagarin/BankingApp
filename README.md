да, понимаю, что еврика, пг и рэбит в идеале должны работать в отдельных контейнерах, а не в одном большом, но для ощущения жесткого кодинга сделал так

for pg admin:
General tab: Postgres
Connection tab:
	host: postgres
	port: 5432
	maintainance: postgres
	username: 5432
	password: qrrqrr

port 8761 - eureka

port 8082 - user-service (in docker)
port 8081 - user-service (in local)

port 8083 - deposit-service

port 8085 - pg4admin online in docker


commands:

docker-compose up --build (two dashes) 

docker-compose stop 

docker pull dpage/pgadmin4:latest 

docker network create pgnetwork

docker run --name my-pgadmin --network bankingapp_pgnetwork -p 8085:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -d dpage/pgadmin4

docker-compose exec postgres psql -U postgres -d Bank (docker-compose exec <service> psql -U <username> -d <db name>)

http://localhost:8081/swagger-ui/index.html
