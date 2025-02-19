create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

create table books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);

create table comments
(
    id      bigserial,
    book_id bigint references books (id) on delete cascade,
    text    varchar(255),
    primary key (id)
);

create table courses
(
    id    bigserial,
    name  varchar(255),
    info  text,
    price integer,
    primary key (id)
);

create table groups
(
    id        bigserial,
    name      varchar(255),
    info      text,
    start_at  date,
    end_at    date,
    course_id bigint references courses (id) on delete set null,
    primary key (id)
);

create table users
(
    id          bigserial,
    username    varchar(255) unique,
    password    varchar(255),
    first_name  varchar(255),
    middle_name varchar(255),
    last_name   varchar(255),
    email       varchar(64),
    phone       varchar(32),
    primary key (id)
);

create table tasks
(
    id         bigserial,
    name       varchar(255),
    info       text,
    start_at   timestamp,
    teacher_id bigint references users (id) on delete set null,
    group_id   bigint references groups (id) on delete set null,
    primary key (id)
);

create table tasks_materials
(
    id      bigserial,
    task_id bigint references tasks (id) on delete cascade,
    name    varchar(255),
    url     varchar(255),
    primary key (id)
);

create table teachers_profiles
(
    user_id bigint references users (id) on delete cascade,
    info    text,
    primary key (user_id)
);

create table roles
(
    role varchar(16),
    primary key (role)
);

create table users_roles
(
    user_id bigint references users (id) on delete cascade,
    role    varchar(16) references roles (role) on delete cascade,
    primary key (user_id, role)
);

create table homeworks
(
    id      bigserial,
    task_id bigint references tasks (id) on delete set null,
    name    varchar(255),
    info    text,
    mark    integer,
    primary key (id)
);

create table ranks
(
    id   bigserial,
    ukey varchar(16) unique not null,
    name varchar(64) unique not null,
    color varchar (32),
    primary key (id)
);

create table results
(
    id          bigserial,
    user_id     bigint references users (id) on delete cascade,
    homework_id bigint references homeworks (id) on delete cascade,
    rank_id     bigint references ranks (id) on delete cascade,
    step        integer,
    score       integer,
    primary key (id)
);


