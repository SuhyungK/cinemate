create table ACTOR
(
    ACTOR_ID BIGINT not null,
    NAME CHARACTER VARYING(255),
    PROFILE_PATH CHARACTER VARYING(255),
    primary key (ACTOR_ID)
);

create table DIRECTOR
(
    DIRECTOR_ID BIGINT not null,
    NAME CHARACTER VARYING(255),
    PROFILE_PATH CHARACTER VARYING(255),
    primary key (DIRECTOR_ID)
);

create table GENRE
(
    GENRE_ID BIGINT not null,
    NAME CHARACTER VARYING(45),
    primary key (GENRE_ID),
    constraint GENRE_ID_UNIQUE
        unique (GENRE_ID)
);

create table MEMBER
(
    MEMBER_ID CHARACTER VARYING(255) not null,
    LOGIN_ID CHARACTER VARYING(45) not null,
    PASSWORD CHARACTER VARYING(45) not null,
    NICKNAME CHARACTER VARYING(255),
    JOINED_AT TIMESTAMP not null,
    OUTED_AT TIMESTAMP,
    primary key (MEMBER_ID)
);

create table MOVIE
(
    MOVIE_ID BIGINT not null,
    TITLE CHARACTER VARYING(255),
    ORIGINAL_TITLE CHARACTER VARYING(255),
    OVERVIEW CHARACTER VARYING(10000),
    POSTER_PATH CHARACTER VARYING(255),
    BACKDROP_PATH CHARACTER VARYING(255),
    RUNTIME INTEGER,
    RELEASE_DATE TIMESTAMP,
    VOTE_AVERAGE DOUBLE PRECISION,
    POPULARITY DOUBLE PRECISION,
    primary key (MOVIE_ID)
);

create table CREW
(
    MOVIE_ID BIGINT not null,
    DIRECTOR_ID BIGINT not null,
    primary key (MOVIE_ID, DIRECTOR_ID),
    constraint DIRECTOR_ID
        foreign key (DIRECTOR_ID) references DIRECTOR,
    constraint MOVIE_ID_FK3
        foreign key (MOVIE_ID) references MOVIE
);

create index DIRECTOR_ID_IDX
    on CREW (DIRECTOR_ID);

create table MOVIE_CAST
(
    CAST_ID BIGINT auto_increment,
    MOVIE_ID BIGINT not null,
    ACTOR_ID BIGINT not null,
    CHARACTER CHARACTER VARYING(255),
    primary key (CAST_ID),
    constraint ACTOR_ID
        foreign key (ACTOR_ID) references ACTOR,
    constraint MOVIE_ID_FK2
        foreign key (MOVIE_ID) references MOVIE
);

create index ACTOR_ID_IDX
    on MOVIE_CAST (ACTOR_ID);

create index MOVIE_ID_FK2_IDX
    on MOVIE_CAST (MOVIE_ID);

create table MOVIE_GENRE
(
    MOVIE_ID BIGINT not null,
    GENRE_ID BIGINT not null,
    primary key (MOVIE_ID, GENRE_ID),
    constraint GENRE_ID
        foreign key (GENRE_ID) references GENRE,
    constraint MOVIE_ID_FK1
        foreign key (MOVIE_ID) references MOVIE
);

create index GENRE_ID_IDX
    on MOVIE_GENRE (GENRE_ID);

create table REVIEW
(
    REVIEW_ID BIGINT auto_increment,
    MOVIE_ID BIGINT not null,
    MEMBER_ID CHARACTER VARYING(255) not null,
    CONTENT CHARACTER VARYING(2000) not null,
    CREATED_AT TIMESTAMP not null,
    UPDATED_AT TIMESTAMP,
    RATE DOUBLE PRECISION,
    primary key (REVIEW_ID),
    constraint MEMBER_ID_FK
        foreign key (MEMBER_ID) references MEMBER,
    constraint MOVIE_ID_FK5
        foreign key (MOVIE_ID) references MOVIE
);

create table VIDEO
(
    MOVIE_ID BIGINT not null,
    VIDEO_ID CHARACTER VARYING(255) not null,
    PATH CHARACTER VARYING(255),
    NAME CHARACTER VARYING(255),
    SIZE INTEGER,
    primary key (MOVIE_ID, VIDEO_ID),
    constraint MOVIE_ID_FK4
        foreign key (MOVIE_ID) references MOVIE
);

