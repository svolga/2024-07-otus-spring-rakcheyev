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

insert into courses(name, info, price)
values ('Алгоритмы и структуры данных',
        'Развивайте алгоритмическое мышление, увеличивайте производительность программ',
        120000),
       ('Golang Developer. Professional',
        'От основ и внутреннего устройства Go – до создания микросервисов и взаимодействия с другими системами',
        125000),
       ('Java Developer. Professional',
        'Освойте создание современных Java-приложений',
        123000),
       ('Разработчик на Spring Framework',
        'Освой востребованный фреймворк для создания приложений на Java, чтобы выйти на новый профессиональный уровень',
        125000),
       ('Administrator Linux',
        'Самый востребованный курс профессиональной переподготовки по Linux. Научитесь профессионально администрировать Linux',
        120000);


insert into groups(name, info, start_at, end_at, course_id)
values ('Java Spring 2025-01-10', '', '2025-01-10', '2025-07-26', 4),
       ('Java Spring 2025-03-14', '', '2025-03-14', '2025-11-07', 4),
       ('Java Spring 2025-05-17', '', '2025-05-17', '2025-12-30', 4),
       ('Java 2025-02-15', '', '2025-02-15', '2025-09-05', 3),
       ('Java 2025-05-18', '', '2025-05-18', '2026-01-20', 3),
       ('Java 2025-07-03', '', '2025-07-03', '2025-02-15', 3);



insert into users(username, last_name, first_name, middle_name, email, phone, password)
values ('admin', 'Админов', 'Админ', 'Админович', 'admin@lotus.com', '+7(964) 659-45-87',
        '$2y$10$CkuUgG713P.J6uX/2J4QQOS3d78iZsCmNUBvPbv7sYGRb4UqOlzAm'),
       ('student1', 'Студентов1', 'Студент1', 'Студентович', 'user@lotus.com', '+7(654) 345-67-90',
        '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym'),
       ('ivanov', 'Иванов', 'Иван', 'Иванович', 'ivan.iv@lotus.com', '+7(904) 563-89-99',
        '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym'),
       ('petrov', 'Петров', 'Петр', 'Петрович', 'petr.pt@lotus.com', '+7(984) 334-66-22',
        '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym'),
       ('vasilyev', 'Васильев', 'Василий', 'Васильевич', 'vasyl.va@lotus.com', '+7(978) 234-56-34',
        '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym'),
       ('student2', 'Студентов2', 'Студент2', 'Студентович', 'student.st@lotus.com', '+7(956) 789-45-43',
        '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym'),
       ('student3', 'Студентов3', 'Студент3', 'Студентович3', 'student2.st@lotus.com', '+7(935) 846-19-41',
        '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym');

insert into teachers_profiles(user_id, info)
values (3, 'Java, Spring, React, Security'),
       (4, 'Java, Spring, .NET, Angular'),
       (5, 'Linux, Ubuntu, Kubernetes, C++, Asm');


insert into roles(role)
values ('ADMIN'),
       ('TEACHER'),
       ('STUDENT');

insert into users_roles(user_id, role)
values (2, 'STUDENT'),
       (1, 'ADMIN'),
       (3, 'TEACHER'),
       (4, 'TEACHER'),
       (5, 'TEACHER'),
       (6, 'STUDENT'),
       (7, 'STUDENT');


insert into tasks(name, info, start_at, teacher_id, group_id)
values ('Введение в Spring Framework',
        'Spring Framework и его Spring Projects;
          IoC в общем виде;
          IoC-контейнер в Spring (контекст);
          XML-контекст cо Spring Beans и DI между ними.',
        parsedatetime('2025-01-25-20.00.00', 'yyyy-MM-dd-HH.mm.ss'),
        3,
        1)
        ,
       ('Конфигурирование Spring-приложений',
        'Java-based конфигурацию контекста Spring;
  Annotation-based конфигурацию контекста;
  аннотация стереотипов, @Autowired и многие другие;
  многослойная архитектура;
  SpEL;
  параметры конфигурации из .properties-файлов',
        parsedatetime('2025-01-27-20.00.00', 'yyyy-MM-dd-HH.mm.ss'),
        3,
        1)
        ,
       ('Продвинутая конфигурация (часть 1) - Scopes, Lifecycle',
        'Scopes (области видимости);
  Lifecycle (жизненный цикл) бинов;
  Также будут рассмотрены дополнительные возможности для конфигурирования приложений;',
        parsedatetime('2025-01-30-20.00.00', 'yyyy-MM-dd-HH.mm.ss'),
        3,
        1)
;

insert into tasks_materials(task_id, name, url)
values (1, 'Материалы otus', 'https://www.otus.ru'),
       (1, 'Документация по Spring', 'https://spring.io/'),
       (1, 'Примеры geeksforgeeks', 'https://www.geeksforgeeks.org/spring/'),
       (1, 'Вопросы-ответы Stackoverflow',
        'https://stackoverflow.com/questions/11840709/how-to-add-help-text-above-input-rather-than-below-it-in-twitter-bootstrap'),
       (2, 'Документация Hibernate',
        'https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#associations-one-to-many-unidirectional'),
       (2, 'Примеры проектов Spring', 'https://github.com/spring-projects/spring-boot');


insert into homeworks(task_id, name, info, mark)
values (1, 'ДЗ__1 по Spring', 'Решить ДЗ__1', 10),
       (2, 'ДЗ__2 по Spring', 'Решить ДЗ__2', 10),
       (3, 'ДЗ__3 по Spring', 'Решить ДЗ__3', 10);


insert into ranks(ukey, name, color)
values ('IN_PROCESS', 'В процессе', 'Khaki'),
       ('DONE', 'Сдано', 'Chartreuse'),
       ('FAILED', 'Не сдано', 'LightSalmon');


insert into results(user_id, rank_id, homework_id, step, score)
values (2, 2, 1, 2, 7),
       (2, 2, 2, 3, 8),
       (2, 2, 3, 2, 9),

       (6, 2, 1, 1, 8),
       (6, 2, 2, 1, 9),
       (6, 1, 3, 2, 0),

       (7, 2, 1, 4, 7),
       (7, 1, 2, 2, 0);


