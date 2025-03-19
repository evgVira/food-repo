create table if not exists food_sc.food_t
(
    id uuid primary key not null,
    name varchar not null,
    calories int not null,
    proteins int not null,
    fats int not null,
    carbohydrates int not null
);