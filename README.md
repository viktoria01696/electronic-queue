## Электронная очередь

> Функциональность приложения с точки зрения пользователя включает в себя возможность зарегистрироваться, посмотреть расписание приема на выбранную дату,
> записаться на прием на доступный временной слот, подтвердить свою запись, аннулировать ее и посмотреть все свои активные записи; с точки зрения 
> администратора - аннулировать запись пользователя, зафиксировать факт явки на прием, вызвать следующего в очереди и завершить прием

- REST-приложение на Spring Boot
- В качестве БД используется PostgreSQL
- БД запускается в Docker
- Flyway для инициализации БД
- ORM (Hibernate)
- Настройка доступа при помощи Spring Security
- Stateless-сессии с подтверждением авторизации через JWT-токен
- Scheduled Task для запланированных действий 

#### run Docker with PostgreSQL:

step 1:
```sh
docker pull postgres
```
step 2:
```sh
docker run --rm --name pgdocker -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=project_db -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data postgres
```
