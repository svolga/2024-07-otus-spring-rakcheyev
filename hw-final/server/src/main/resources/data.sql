insert into authors(full_name)
values ('Author_1'),
       ('Author_2'),
       ('Author_3');

insert into genres(name)
values ('Genre_1'),
       ('Genre_2'),
       ('Genre_3'),
       ('Genre_4'),
       ('Genre_5'),
       ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1),
       ('BookTitle_2', 2),
       ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);

insert into comments(text, book_id)
values ('Book1_comment1', 1),
       ('Book2_Comment1', 2),
       ('Book2_Comment2', 2),
       ('Book3_Comment1', 3),
       ('Book3_Comment1', 3),
       ('Book3_Comment3', 3)
;

insert into courses(name, info)
values ('Алгоритмы и структуры данных',
        'Развивайте алгоритмическое мышление, увеличивайте производительность программ'),
       ('Golang Developer. Professional',
        'От основ и внутреннего устройства Go – до создания микросервисов и взаимодействия с другими системами'),
       ('Java Developer. Professional', 'Освойте создание современных Java-приложений'),
       ('Разработчик на Spring Framework',
        'Освой востребованный фреймворк для создания приложений на Java, чтобы выйти на новый профессиональный уровень'),
       ('Administrator Linux',
        'Самый востребованный курс профессиональной переподготовки по Linux. Научитесь профессионально администрировать Linux');


insert into groups(name, info, start_at, end_at, course_id)
values ('Java Spring 2025-01-10', '', '2025-01-10', '2025-07-26', 4),
       ('Java Spring 2025-03-14', '', '2025-03-14', '2025-11-07', 4),
       ('Java Spring 2025-05-17', '', '2025-05-17', '2025-12-30', 4),
       ('Java 2025-02-15', '', '2025-02-15', '2025-09-05', 3),
       ('Java 2025-05-18', '', '2025-05-18', '2026-01-20', 3),
       ('Java 2025-07-03', '', '2025-07-03', '2025-02-15', 3);


insert into teachers(nickname, first_name, last_name, middle_name, phone, email, info)
values ('javadev', 'Иван', 'Иванов', 'Иванович', '+7(654) 345-67-90', 'ivan.iv@lotus.com',
        'Java, Spring, React, Security'),
       ('spring', 'Петр', 'Петров', 'Петрович', '+7(964) 659-45-87', 'petr.pt@lotus.com',
        'Java, Spring, .NET, Angular'),
       ('linux', 'Василий', 'Васильев', 'Васильевич', '+7(904) 563-89-99', 'vasyl.va@lotus.com',
        'Linux, Ubuntu, Kubernetes, C++, Asm');


insert into users(username, last_name, first_name, middle_name, email, phone, password, role)
values ('user', 'Юзеров', 'Юзер', 'Юзерович', 'user@lotus.com', '+7(654) 345-67-90',  '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'USER'),
       ('admin', 'Админов', 'Админ', 'Админович', 'admin@lotus.com', '+7(964) 659-45-87',  '$2y$10$CkuUgG713P.J6uX/2J4QQOS3d78iZsCmNUBvPbv7sYGRb4UqOlzAm', 'ADMIN'),
       ('ivanov', 'Иванов', 'Иван', 'Иванович', 'ivan.iv@lotus.com', '+7(904) 563-89-99', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'TEACHER'),
       ('petrov', 'Петров', 'Петр', 'Петрович', 'petr.pt@lotus.com', '+7(984) 334-66-22', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'TEACHER'),
       ('vasilyev', 'Васильев', 'Василий', 'Васильевич', 'vasyl.va@lotus.com', '+7(978) 234-56-34', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'TEACHER'),
       ('student', 'Студентов', 'Студент', 'Студентович', 'student.st@lotus.com', '+7(956) 789-45-43', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'STUDENT'),
       ('student2', 'Студентов2', 'Студент2', 'Студентович2', 'student2.st@lotus.com', '+7(935) 846-19-41', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'STUDENT');

insert into roles(role)
values ('ADMIN'),
       ('USER'),
       ('MANAGER'),
       ('TEACHER'),
       ('STUDENT');

insert into users_roles(user_id, role)
values (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'TEACHER'),
       (4, 'TEACHER'),
       (5, 'TEACHER'),
       (6, 'STUDENT'),
       (7, 'STUDENT');