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
       ('Book3_Comment3', 3);

insert into users(username, password, authority)
values ('user', '$2y$10$sX2qt/F94.TkrBRntKT0luRPX6/Q3D8ti.GdKL.ePmYk6wgFV9rym', 'USER'),
       ('admin', '$2y$10$CkuUgG713P.J6uX/2J4QQOS3d78iZsCmNUBvPbv7sYGRb4UqOlzAm', 'ADMIN');

insert into acl_sid (id, principal, sid)
values (1, 1, 'admin'),
       (2, 1, 'user');

insert into acl_class (id, class)
values (1, 'ru.otus.hw.dto.BookDto'),
       (2, 'ru.otus.hw.dto.BookInfoDto'),
       (3, 'ru.otus.hw.models.Book');

insert into acl_object_identity
(id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
values (1, 1, 1, null, 1, 0), -- BookDto, 1
       (2, 1, 2, null, 1, 0), -- BookDto, 2
       (3, 1, 3, null, 1, 0), -- BookDto, 3

       (4, 2, 1, null, 1, 0), -- BookInfoDto, 1
       (5, 2, 2, null, 1, 0), -- BookInfoDto, 2
       (6, 2, 3, null, 1, 0), -- BookInfoDto, 3

       (7, 3, 1, null, 1, 0), -- Book, 1
       (8, 3, 2, null, 1, 0), -- Book, 2
       (9, 3, 3, null, 1, 0) -- Book, 3
;

insert into acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (1, 1, 1, 1, 1, 1, 1),
       (2, 1, 1, 1, 1, 1, 1),
       (3, 1, 1, 1, 1, 1, 1),
       (4, 1, 1, 1, 1, 1, 1),
       (5, 1, 1, 1, 1, 1, 1),
       (6, 1, 1, 1, 1, 1, 1),
       (7, 1, 1, 1, 1, 1, 1),
       (8, 1, 1, 1, 1, 1, 1),
       (9, 1, 1, 1, 1, 1, 1),

       (1, 2, 1, 2, 1, 1, 1),
       (2, 2, 1, 2, 1, 1, 1),
       (3, 2, 1, 2, 1, 1, 1),
       (4, 2, 1, 2, 1, 1, 1),
       (5, 2, 1, 2, 1, 1, 1),
       (6, 2, 1, 2, 1, 1, 1),
       (7, 2, 1, 2, 1, 1, 1),
       (8, 2, 1, 2, 1, 1, 1),
       (9, 2, 1, 2, 1, 1, 1);