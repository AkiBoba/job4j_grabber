- 0. Техническое задание. Агрегатор Java Вакансий. [#260357 #264343]
В этом задании мы опишем проект - Агрегатор вакансий.
Для этого проекта создайте отдельный репозиторий job4j_grabber.
В нем добавьте maven, jacoco, checkstyle.
Приложение должно собираться в jar.

-1. Quartz [#175122] В Java есть библиотека позволяющая делать действия с периодичностью.-
-1.1. Job c параметрами [#260360] Добавление подключение к БД, 
передача подключения в джоб, запись данных в таблицу БД + AutoCloseable ----
-2. Парсинг HTML страницы. [#260358]
-2.1. Преобразование даты [#289476] - 
-2.1.1. Парсинг https://www.sql.ru/forum/job-offers/3 [#285210] перебор страниц сайта --
-2.2. Модель данных - Post. [#285211] 
-2.3. Загрузка деталей поста. [#285212] перенес метод создания модели в SqlRuParse
-2.4. SqlRuParse [#285213] - -
-3. Архитектура проекта - Аргегатор Java Вакансий [#260359]
-4. Схема таблицы Post [#1731]
-5. PsqlStore [#285209 #266706]---
-6. Grabber. [#289477]