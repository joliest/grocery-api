create table store
(
    id             serial    primary key,
    name           varchar(500)             not null unique ,
    description          varchar(1000)
);