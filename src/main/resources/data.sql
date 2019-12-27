insert into person (id , name, gender) values (1, 'person', 'M');
insert into person (id, name, gender) values (2, 'wife', 'F');
insert into person (id, name, gender) values (3, 'child', 'M');


insert into relation values (1, 'SPOUSE', 1, 2);
insert into relation values (2, 'SPOUSE', 2, 1);
insert into relation values (3, 'CHILD', 1, 3);
insert into relation values (4, 'CHILD', 2, 3);
insert into relation values (5, 'PARENT', 3, 1);
insert into relation values (6, 'PARENT', 3, 2);