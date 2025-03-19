create table if not exists user_sc.user_t
(
    id uuid primary key not null,
    name varchar not null,
    email varchar unique not null,
    age int not null,
    weight int not null,
    height int not null,
    goal varchar not null
);