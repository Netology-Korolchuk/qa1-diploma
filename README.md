# Дипломный проект профессии «Тестировщик»

[План автоматизации](docs/Plan.md)

[Отчет по итогам тестирования](docs/Report.md)

[Отчет по итогам автоматизации](docs/Summary.md)

---------------------
## Инструкция по запуску

1. Склонировать репозиторий  
    <code>git clone https://github.com/Netology-Korolchuk/qa1-diploma.git</code>

2. Запустить контейнеры docker:  

    Для запуска связки node + mysql выполнить команду:  
    <code>docker-compose -f docker-compose-mysql.yml up -d</code>
    После прогона тестов остановить контейнеры:  
    <code>docker-compose -f docker-compose-mysql.yml down</code>

    Для запуска связки node + postgres выполнить команду:  
    <code>docker-compose -f docker-compose-postgres.yml up -d</code>
    После прогона тестов остановить контейнеры:  
    <code>docker-compose -f docker-compose-postgres.yml down</code>

3. Запустить приложение:  

    Для запуска приложения с базой mysql выполнить команду:  
    <code>java -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app -jar aqa-shop.jar</code>

    Для запуска приложения с базой postgres выполнить команду:  
    <code>java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar aqa-shop.jar</code>

4. Запустить тесты:  

   Для mysql выполнить команду:  
   <code>gradlew test -Dtest.db.url=jdbc:mysql://192.168.99.100:3306/app</code>

   Для postgres выполнить команду:  
   <code>gradlew test -Dtest.db.url=jdbc:postgresql://192.168.99.100:5432/app</code>

5. Сформировать отчеты командой:  
   <code>gradlew allureReport</code>  

6. Открыть отчеты в браузере командой:  
   <code>gradlew allureServe</code>





