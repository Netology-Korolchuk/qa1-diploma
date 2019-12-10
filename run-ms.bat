docker-machine start default
docker-machine env

cd ms

docker-compose up -d
java -jar aqa-shop.jar

docker container stop ms_mysql_1
