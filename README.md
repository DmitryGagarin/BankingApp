port 8761 - eureka

port 8082 - user-service

port 8085 - pg4admin online in docker


commands:

docker-compose up --build 

docker-compose stop 

docker pull dpage/pgadmin4:latest 

docker network create pgnetwork

docker run --name my-pgadmin --network bankingapp_pgnetwork -p 8085:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -d dpage/pgadmin4

docker-compose exec postgres psql -U postgres -d Bank (docker-compose exec <service> psql -U <username> -d <db name>)