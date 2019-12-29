insert into person (id , name, gender) values (10, 'person', 'M');
insert into person (id, name, gender) values (20, 'wife', 'F');
insert into person (id, name, gender) values (30, 'child', 'M');


insert into relation values (110, 'SPOUSE', 10, 20);
insert into relation values (120, 'SPOUSE', 20, 10);
insert into relation values (130, 'CHILD', 10, 30);
insert into relation values (140, 'CHILD', 20, 30);
insert into relation values (150, 'PARENT', 30, 10);
insert into relation values (160, 'PARENT', 30, 20);