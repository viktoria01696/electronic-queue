CREATE TABLE "user"(
    "id" SERIAL,
    "login" VARCHAR(100) UNIQUE NOT NULL,
    "password" VARCHAR(100) NOT NULL,
    "role_id" BIGINT NOT NULL,
    "firstname" VARCHAR(255) NOT NULL,
    "lastname" VARCHAR(255) NOT NULL,
    "patronymic" VARCHAR(255),
    "birthday" DATE,
    "age" INTEGER NOT NULL,
    "sex" INTEGER NOT NULL,
    "email" VARCHAR(255),
    "create_date" DATE NOT NULL
);
CREATE INDEX "user_login_index" ON
    "user" ("login");
ALTER TABLE
    "user" ADD PRIMARY KEY("id");


CREATE TABLE "role"(
    "id" SERIAL,
    "name" VARCHAR(100) NOT NULL
);
ALTER TABLE
    "role" ADD PRIMARY KEY("id");

CREATE TABLE "appointment"(
    "id" SERIAL,
    "date" TIMESTAMP NOT NULL,
    "confirmation_code" VARCHAR(36) NOT NULL,
    "create_date" TIMESTAMP NOT NULL,
    "user_id" BIGINT NOT NULL,
    "status_id" BIGINT NOT NULL
);
CREATE INDEX "appointment_date_index" ON
    "appointment" ("date");
CREATE INDEX "appointment_confirmation_code_index" ON
    "appointment" ("confirmation_code");
CREATE INDEX "appointment_user_id_index" ON
    "appointment" ("user_id");
ALTER TABLE
    "appointment" ADD PRIMARY KEY("id");

ALTER TABLE
    "appointment" ADD CONSTRAINT "appointment_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "user"("id");
ALTER TABLE
    "user" ADD CONSTRAINT "user_role_id_foreign" FOREIGN KEY("role_id") REFERENCES "role"("id");
