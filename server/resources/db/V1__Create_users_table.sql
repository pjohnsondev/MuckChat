create table USERS
(
    ID       INTEGER default AUTOINCREMENT: start 1 increment 1 generated always as identity
        unique,
    USERNAME VARCHAR(80)
        unique,
    PASSWORD BLOB not null,
    SALT     BLOB not null
);


