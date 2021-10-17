CREATE TABLE "student"
(
    "student_id"  SERIAL,
    "firstname"   VARCHAR(255) NOT NULL,
    "lastname"    VARCHAR(255) NOT NULL,
    "patronymic"  VARCHAR(255),
    "birthday"    DATE         NOT NULL,
    "age"         INTEGER      NOT NULL,
    "sex"         INTEGER      NOT NULL,
    "school_id"   BIGINT       NOT NULL,
    "email"       VARCHAR(255),
    "create_date" DATE         NOT NULL,
    "is_active"   BOOLEAN      NOT NULL
);
CREATE INDEX "student_student_id_index" ON
    "student" ("student_id");
CREATE INDEX "student_lastname_index" ON
    "student" ("lastname");
ALTER TABLE
    "student"
    ADD PRIMARY KEY ("student_id");

CREATE TABLE "message"
(
    "message_id"        SERIAL,
    "student_id"        BIGINT,
    "chat_id"           BIGINT  NOT NULL,
    "message_body"      TEXT,
    "create_date"       DATE    NOT NULL,
    "parent_message_id" BIGINT,
    "is_read"           BOOLEAN NOT NULL
);
CREATE INDEX "message_chat_id_index" ON
    "message" ("chat_id");
ALTER TABLE
    "message"
    ADD PRIMARY KEY ("message_id");

CREATE TABLE "chat"
(
    "chat_id"     SERIAL,
    "chat_name"   VARCHAR(255),
    "create_date" DATE NOT NULL
);
CREATE INDEX "chat_id_index" ON
    "chat" ("chat_id");
ALTER TABLE
    "chat"
    ADD PRIMARY KEY ("chat_id");

CREATE TABLE "attachment"
(
    "attachment_id" SERIAL,
    "message_id"    BIGINT,
    "post_id"       BIGINT,
    "url"           TEXT         NOT NULL,
    "type"          VARCHAR(255) NOT NULL
);
CREATE INDEX "attachment_message_id_index" ON
    "attachment" ("message_id");
CREATE INDEX "attachment_post_id_index" ON
    "attachment" ("post_id");
ALTER TABLE
    "attachment"
    ADD PRIMARY KEY ("attachment_id");

CREATE TABLE "student_chat"
(
    "student_id" BIGINT REFERENCES student ON DELETE CASCADE,
    "chat_id"    BIGINT REFERENCES chat ON DELETE CASCADE
);
CREATE INDEX "student_id_chat_id_index" ON
    "student_chat" ("student_id", "chat_id");
ALTER TABLE
    "student_chat"
    ADD PRIMARY KEY ("student_id", "chat_id");

CREATE TABLE "friendship"
(
    "first_member_id"  BIGINT REFERENCES student ON DELETE CASCADE,
    "second_member_id" BIGINT REFERENCES student ON DELETE CASCADE
);
CREATE INDEX "first_member_id_second_member_id_index" ON
    "friendship" (
                  "first_member_id",
                  "second_member_id"
        );
ALTER TABLE
    "friendship"
    ADD PRIMARY KEY ("first_member_id", "second_member_id");

CREATE TABLE "school"
(
    "school_id"   SERIAL,
    "school_name" VARCHAR(255),
    "address"     VARCHAR(255) NOT NULL
);
CREATE INDEX "school_school_id_index" ON
    "school" ("school_id");
ALTER TABLE
    "school"
    ADD PRIMARY KEY ("school_id");

CREATE TABLE "post"
(
    "post_id"     SERIAL,
    "student_id"  BIGINT NOT NULL,
    "post_body"   TEXT,
    "create_date" DATE   NOT NULL
);
CREATE INDEX "post_student_id_index" ON
    "post" ("student_id");
ALTER TABLE
    "post"
    ADD PRIMARY KEY ("post_id");

ALTER TABLE
    "attachment"
    ADD CONSTRAINT "attachment_message_id_foreign" FOREIGN KEY ("message_id") REFERENCES "message" ("message_id") ON DELETE CASCADE;
ALTER TABLE
    "message"
    ADD CONSTRAINT "message_parent_message_id_foreign" FOREIGN KEY ("parent_message_id") REFERENCES "message" ("message_id");
ALTER TABLE
    "message"
    ADD CONSTRAINT "message_chat_id_foreign" FOREIGN KEY ("chat_id") REFERENCES "chat" ("chat_id") ON DELETE CASCADE;
ALTER TABLE
    "message"
    ADD CONSTRAINT "message_student_id_foreign" FOREIGN KEY ("student_id") REFERENCES "student" ("student_id");
ALTER TABLE
    "student"
    ADD CONSTRAINT "student_school_id_foreign" FOREIGN KEY ("school_id") REFERENCES "school" ("school_id") ON DELETE SET NULL;
ALTER TABLE
    "attachment"
    ADD CONSTRAINT "attachment_post_id_foreign" FOREIGN KEY ("post_id") REFERENCES "post" ("post_id") ON DELETE CASCADE;
ALTER TABLE
    "post"
    ADD CONSTRAINT "post_student_id_foreign" FOREIGN KEY ("student_id") REFERENCES "student" ("student_id") ON DELETE CASCADE;