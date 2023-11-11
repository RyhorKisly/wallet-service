# wallet-service

## Задания, в соответствии с которыми создавалось приложение (на каждой ветке есть новые задания).

#### Описание
Необходимо обновить сервис, который вы разработали в первом задании согласно следующим требованиям и ограничениям

#### Требования:
- Репозитории теперь должны писать ВСЕ сущности в БД PostgreSQL
- Идентификаторы при сохранении в БД должны выдаваться через sequence
- DDL-скрипты на создание таблиц и скрипты на предзаполнение таблиц должны выполняться только инструментом миграции Liquibase
- Скрипты миграции Luiqbase должны быть написаны только в нотации XML
- Скриптов миграции должно быть несколько. Как минимум один на создание всех таблиц, другой - не предзаполнение данными
- Служебные таблицы должны быть в отдельной схеме
- Таблицы сущностей хранить в схеме public запрещено
- В тестах необходимо использовать test-containers
- В приложении должен быть docker-compose.yml, в котором должны быть прописаны инструкции для развертывания postgre в докере. Логин, пароль и база должны быть отличными от тех, что прописаны в образе по-умолчанию. Приложение должно работать с БД, развернутой в докере с указанными параметрами.
- Приложение должно поддерживать конфиг-файлы. Всё, что относится к подключению БД, а также к миграциям, должно быть сконфигурировано через конфиг-файл

## Инструкция по запуску проекта

Для запуска проекта потребуется:
- java 17
- maven
- docker
- доступ в интернет (при необходимости)

1. Перед запуском программы, в корневой папке "wallet-service" выполните команду "mvn compile" 
с целью подтянуть недостающие зависимости и не трогать тесты;
2. В корневой папке "wallet-service" выполните команду "docker-compose up"
для создания базы данных и pgadmin (на всякий случай);
3. Запустите файл MainLiquibase (classPath: "src/main/java/io/ylab/walletservice/MainLiquibase")
для миграции данных в базу данных;
4. Теперь можно проверить тесты;
5. Для запуска проекта используйте класс Main (classPath: "src/main/java/io/ylab/walletservice/Main").

Если необходим доступ к pgadmin:
1. введите в адресной строке localhost:888;
2. login: admin@admin.admin;
3. password: admin;
4. зарегистрируйте сервер:
- имея хоста: pg_db;
- порт: 5432;
- логин: ylab;
- пароль: ylab.
