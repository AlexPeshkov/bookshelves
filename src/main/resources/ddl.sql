create table BOOK (
  ID bigint primary key not null generated always as identity,
  TITLE varchar(50) not null,
  AUTHOR varchar(50) not null,
  BOOK_TYPE varchar(2) not null
);
