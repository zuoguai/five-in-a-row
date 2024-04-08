create table fir.fir_record
(
    id    int auto_increment
        primary key,
    a_id  int         null,
    b_id  int         null,
    loser varchar(20) null,
    time  datetime    null,
    constraint id
        unique (id)
);

create table fir.fir_user
(
    id       int auto_increment
        primary key,
    username varchar(20)       null,
    password varchar(200)      null,
    photo    varchar(500)      null,
    rating   int default 10000 null,
    win      int               null,
    lose     int               null,
    constraint id
        unique (id)
);

