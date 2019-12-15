# Дипломный проект профессии «Тестировщик»

[![Build status](https://ci.appveyor.com/api/projects/status/x6qt524ls5d9gyam?svg=true)](https://ci.appveyor.com/project/Netology-Korolchuk/qa1-diploma)

[План автоматизации](docs/Plan.md)

[Отчет по итогам тестирования](docs/Report.md)

[Отчет по итогам автоматизации](docs/Summary.md)

---------------------
## Инструкция по запуску

1. Склонировать репозиторий  
    <code>git clone https://github.com/Netology-Korolchuk/qa1-diploma.git</code>

2. Запустить контейнеры docker:     
    > По умолчанию настроена связка node + MySql  
    Для запуска связки node + Postgres файл ps_docker-compose.yml переименовать в docker-compose.yml   
 
    Выполнить команду:  
    <code>docker-compose up -d</code>
 
3. В каталоге /artifacts запустить приложение:  
    > По умолчанию приложение настроено для работы с базой MySql    
    Для запуска приложения с базой Postgres файл ps_application.properties переименовать в application.properties   
   
    Выполнить команду:  
    <code>java -jar aqa-shop.jar</code>

4. В каталоге /gate-simulator запустить симулятор платежного шлюза  
   <code>npm start</code>

5. Запустить тесты  
   <code>gradlew test</code>

6. Сформировать и открыть отчеты  
   <code>gradlew allureReport</code>  
   <code>gradlew allureServe</code>





