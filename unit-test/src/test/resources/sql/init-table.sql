create table if not exists employee(
  id int unsigned auto_increment,
  name varchar(100) not null,
  age int default 0,
  birth datetime,
  primary key(id)
);