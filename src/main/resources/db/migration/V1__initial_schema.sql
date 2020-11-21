CREATE TABLE accounts (
    id uuid PRIMARY KEY,
    login varchar not null unique,
    password varchar not null
);

INSERT INTO accounts (id, login, password)
VALUES ('8fa4a163-af34-4190-bfba-5f2f1f282a11', 'test', '$2a$11$Sw7UpA6tfG.BmzM5d8A63usWu1qW878VDevExDO5DjJ9mLG4uv/bG');
