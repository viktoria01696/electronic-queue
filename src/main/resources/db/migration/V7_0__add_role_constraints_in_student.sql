ALTER TABLE
    "student"
    ADD CONSTRAINT "student_role_id_foreign" FOREIGN KEY ("role_id") REFERENCES "role" ("role_id");