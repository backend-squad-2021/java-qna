INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');

INSERT INTO QUESTION (id, contents, created_date_time, last_modified_date_time, title, user_id, deleted) VALUES (1, 'Content1', '2020-02-28T03:50:36.802145', '2020-02-28T03:50:36.802145', 'Subject1', 1, false);
INSERT INTO QUESTION (id, contents, created_date_time, last_modified_date_time, title, user_id, deleted) VALUES (2, 'Content2', '2020-02-28T03:55:36.802145', '2020-02-28T03:55:36.802145', 'Subject2', 1, false);
INSERT INTO QUESTION (id, contents, created_date_time, last_modified_date_time, title, user_id, deleted) VALUES (3, 'Content3', '2020-02-28T04:50:36.802145', '2020-02-28T04:50:36.802145', 'Subject3', 2, false);
INSERT INTO QUESTION (id, contents, created_date_time, last_modified_date_time, title, user_id, deleted) VALUES (4, 'Content4', '2020-02-28T06:50:36.802145', '2020-02-28T06:50:36.802145', 'Subject4', 2, false);
