docker-machine start default
docker-machine env

cd ps

docker-compose up -d
java -jar aqa-shop.jar

docker container stop ps_postgres_1