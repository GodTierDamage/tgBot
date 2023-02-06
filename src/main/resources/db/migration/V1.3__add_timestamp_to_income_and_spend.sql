alter table income
add time_of_operation timestamp default current_timestamp;

alter table spend
add time_of_operation timestamp default current_timestamp;
