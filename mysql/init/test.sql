CREATE DATABASE IF NOT EXISTS up_db;
use up_db;
create table if not exists competitions
(
    id       varchar(32)  null,
    adults   int          null,
    children int          null,
    elderly  int          null,
    date     datetime     null,
    place    varchar(255) null,
    title    varchar(255) null,
    bronze   varchar(255) null,
    gold     varchar(255) null,
    silver   varchar(255) null
);

create table if not exists games
(
    id       varchar(32)  not null,
    adults   int          null,
    children int          null,
    elderly  int          null,
    date     datetime     null,
    place    varchar(255) null,
    title    varchar(255) null,
    first    int          null,
    second   int          null,
    constraint games_id_uindex
        unique (id)
);

create table if not exists matches
(
    id       varchar(32)  not null,
    adults   int          null,
    children int          null,
    elderly  int          null,
    date     datetime     null,
    place    varchar(255) null,
    title    varchar(255) null,
    winner   varchar(255) null,
    constraint matches_id_uindex
        unique (id)
);
