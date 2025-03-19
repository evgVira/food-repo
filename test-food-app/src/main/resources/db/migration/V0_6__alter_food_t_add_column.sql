alter table if exists food_sc.food_t
add column user_id uuid not null;

alter table if exists food_sc.food_t
add column created_at timestamp not null;

alter table if exists food_sc.food_t
add column updated_at timestamp not null;