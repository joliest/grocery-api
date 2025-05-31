create table category
(
    id             serial    primary key,
    name           varchar(500)             not null unique ,
    description          varchar(1000)
);
create table subcategory
(
    id             serial    primary key,
    category_id    int          default null,
    name           varchar(500)             not null,
    description          varchar(1000),
    foreign key (category_id) references category(id),
    unique (category_id, name)
);
create table store
(
    id             serial    primary key,
    name           varchar(500)             not null unique ,
    description          varchar(1000)
);

create table product
(
    id             serial                primary key,
    name           varchar(200)             not null,
    category_id      int             default null,
    subcategory_id    int             default null,
    foreign key (category_id) references category(id),
    foreign key (subcategory_id) references subcategory(id)
);
create table purchase_history
(
    id             serial                primary key,
    product_id           int            default null,
    price          decimal                  default 0,
    date_purchased timestamp with time zone default null,
    link           varchar                  default null,
    store_id          int                   default null,
    foreign key (product_id) references product(id),
    foreign key (store_id) references store(id)
);
create table grocery
(
    id      serial      primary key,
    name    varchar(500)    not null,
    description     varchar(1000)
);
create table grocery_item
(
    id      serial      primary key,
    product_id    int    default null,
    grocery_id    int    default null,
    quantity    decimal    default 1,
    notes   varchar   default null,
    actual_price          decimal                  default 0,
    estimated_price          decimal                  default 0,
    store_id    int    default null,
    foreign key (grocery_id) references grocery(id),
    foreign key (product_id) references product(id),
    foreign key (store_id) references store(id)
);