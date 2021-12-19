drop table tbl_sub_url;
drop table tbl_main_url;

create table tbl_sub_url
(
    id            integer not null
        constraint tbl_sub_url_pk
            primary key autoincrement,
    uid           integer not null /*autoincrement needs PK*/,
    site_uid      integer not null,
    website       text    not null,
    website_name  text,
    response_time text,
    status        text,
    website_size  text,
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create unique index tbl_sub_url_site_id_uindex
    on tbl_sub_url (site_uid);

create table tbl_main_url
(
    id                integer not null
        constraint tbl_main_url_pk
            primary key autoincrement,
    uid               integer not null /*autoincrement needs PK*/,
    website           text    not null,
    website_name      text,
    website_size      text,
    response_time     text,
    status            text,
    total_links_found integer,
    timestamp         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create unique index tbl_main_url_uid_uindex
    on tbl_main_url (uid);





