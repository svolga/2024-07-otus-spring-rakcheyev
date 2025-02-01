insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into comments(text, book_id)
values ('Book1_comment1', 1),
       ('Book2_Comment1', 2), ('Book2_Comment2', 2),
       ('Book3_Comment1', 3), ('Book3_Comment1', 3), ('Book3_Comment3', 3)
;

insert into users(username, password, authority)
values ('user', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'USER'),
       ('manager', '$2y$10$fbk9bU0qHYSi.fDgisauAeeP6AJabkGWGpzSEz9Z4HylHrlBxIbwy', 'MANAGER'),
       ('admin', '$2y$10$CkuUgG713P.J6uX/2J4QQOS3d78iZsCmNUBvPbv7sYGRb4UqOlzAm', 'ADMIN');