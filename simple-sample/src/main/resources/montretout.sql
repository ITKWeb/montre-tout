    create table Address (
        id integer NOT NULL AUTO_INCREMENT,
        flatNumber integer,
        houseNumber integer,
        streetName varchar(255),
        primary key (id)
    );

    create table Address_AUD (
        id integer not null,
        REV integer not null,
        flatNumber integer,
        houseNumber integer,
        streetName varchar(255),
        REVTYPE tinyint,
        primary key (id, REV)
    );

    create table Person (
        id integer AUTO_INCREMENT,
        name varchar(255),
        surname varchar(255),
        address_id integer,
        primary key (id)
    );

    create table Person_AUD (
        id integer not null,
        REV integer not null,
        name varchar(255),
        surname varchar(255),
        REVTYPE tinyint,
        address_id integer,
        primary key (id, REV)
    );

    create table REVINFO (
        REV integer AUTO_INCREMENT,
        REVTSTMP bigint,
        primary key (REV)
    );

    alter table Person
        add constraint FK8E488775E4C3EA63
        foreign key (address_id)
        references Address;
