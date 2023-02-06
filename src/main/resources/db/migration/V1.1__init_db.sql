drop table if exists active_chat cascade;
drop table if exists income cascade;
drop table if exists spend cascade;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 1 increment 1;

create table active_chat (
    id int8 not null,
    chat_id int8,
    primary key (id)
                         );

create table income (
    id int8 generated by default as identity,
    chat_id int8,
    income numeric(19, 2),
    primary key (id)
                    );

create table spend (
    id int8 generated by default as identity,
    chat_id int8,
    spend numeric(19, 2),
    primary key (id)
                   );