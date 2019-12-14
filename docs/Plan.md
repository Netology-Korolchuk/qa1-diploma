# Планирование автоматизации

## 1. Перечень автоматизируемых сценариев
      
- #### UI тесты

  - Позитивные
    - Обычная покупка картой А (валидные данные)
    - Покупка в кредит картой А (валидные данные)
    - Обычная покупка картой B (валидные данные)
    - Покупка в кредит картой B (валидные данные)

  - Негативные, для обоих разделов: обычная покупка и покупка в кредит
    - Обычная покупка картой А (пустые и невалидные данные)
    - Покупка в кредит картой А (пустые и невалидные данные)
    - Обычная покупка картой B (пустые и невалидные данные)
    - Покупка в кредит картой B (пустые и невалидные данные) 

- #### API тесты

  - Позитивные
    - Проверка поддержки MySQL
    - Проверка поддержки Postgres
    - Проверка корректности данных записанных в БД

## 2. Перечень используемых инструментов
### Все тесты будут написаны на языке java, т.к. само приложение написано на java, соответственно и вся инфраструктура была выбрана для языка java.
### Весь инструментарий выбран исходя из компетентности разработчика тестов.
* IntelliJ IDEA. Платформа для удобного написания кода, в том числе для тестов.
* JUnit. Библиотека для написания и запуска авто-тестов.
* Gradle. Система управления зависимости, которая позволит удобно ставить необходимые фреймворки без проблем с постоянной настройкой зависимостей.
* Selenide. Фреймворк для автоматизированного тестирования веб-приложений.
* Allure. Фреймворк, предназначенный для создания подробных отчетов о выполнении тестов.
* Docker -для развертывания БД.
* Git и Github. Система контроля версий, для хранения кодов автотестов и настроек окружения.
 
## 3. Перечень и описание возможных рисков при автоматизации
 
* Риск появления проблем с настройкой приложения и необходимых БД.
* Риск появления проблем с правильной идентификацией полей ввода. 
* Риск неработающего заявленного функционала приложения.

## 4. Интервальная оценка с учётом рисков (в часах)
* Подготовка окружения, развертывание БД - 8 часов.
* Написание автотестов, тестирование и отладка автотестов -  24 часа.
* Формирование и анализ отчетов - 4 часа. 
 
## 5. План сдачи работ (когда будут авто-тесты, результаты их прогона и отчёт по автоматизации)
1. До 30 ноября - планирование автоматизации тестирования.
1. С 1 по 13 декабря - настройка окружения, написание и отладка автотестов, тестирование.
1. C 13 по 15 декабря - подготовка отчетных документов.