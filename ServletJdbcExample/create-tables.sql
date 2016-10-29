--CREATE DATABASE servletjdbc WITH OWNER = postgres ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251' CONNECTION LIMIT = -1;

CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 55
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;

create table if not exists public.usertable (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	caption varchar(255),
	firstname varchar(255),
	lastname varchar(255),
	email varchar(255),
	login varchar(255) UNIQUE,
	password varchar(255),
	CONSTRAINT usertablepk PRIMARY KEY (id)
);

create table if not exists public.worktask(
  id bigint not null default nextval('hibernate_sequence'::regclass),
  caption varchar(255),
  deadline timestamp without time zone,
  taskcontext varchar(255),
  taskdate timestamp without time zone,
  taskuser_id bigint,
  taskstatus_id integer,
  CONSTRAINT worktaskpk PRIMARY KEY (id),
  CONSTRAINT fk_oam3n63solk5k7h80o8lqimrh FOREIGN KEY (taskuser_id)
      REFERENCES public.usertable (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

insert into public.usertable (caption, firstname, lastname, email, login, password) values
('visitor', 'Vasili', 'Petrov', 'vtd@mycomp.ru', 'vtd', '111');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('guest', 'Sergey', 'Storogev', 'stx1@mycomp.ru', 'stx1', '222');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('product manager', 'Alexey', 'Vasin', 'vpt@mycomp.ru', 'vpt', '333');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('assistant', 'Elena', 'Kononova', 'ast@mycomp.ru', 'ast', '444');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('tester', 'Valentina', 'Sokolova', 'test1@mycomp.ru', 'test1', '555');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('tester', 'Tatiana', 'Smirnova', 'test2@mycomp.ru', 'test2', '666');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('admin', 'Stanislav', 'Dolgih', 'admin-gr@mycomp.ru', 'admin-gr', '777');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('programmer', 'Evgeniy', 'Stepanov', 'gr-1@mycomp.ru', 'gr-1', '888');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('programmer', 'Mihail', 'Vasilkov', 'gr-2@mycomp.ru', 'gr-2', '999');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('programmer', 'Oleg', 'Efremov', 'gr-3@mycomp.ru', 'gr-3', '101');
insert into public.usertable (caption, firstname, lastname, email, login, password) values
('lead', 'Vladimir', 'Ogarev', 'lead@mycomp.ru', 'lead', '102');

insert into public.worktask (caption, taskdate, deadline, taskcontext, taskuser_id, taskstatus_id) values
('Test task', '04.09.2016', '05.09.2016', 'test current build', (select id from usertable where usertable.email='test2@mycomp.ru'), 0);
insert into public.worktask (caption, taskdate, deadline, taskcontext, taskuser_id, taskstatus_id) values
('Test stend', '02.09.2016', '04.09.2016', 'Create test stend', (select id from usertable where usertable.email='admin-gr@mycomp.ru'), 0);
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Plan description', '31.08.2016', '06.09.2016', 'Make technical description', (select id from usertable where usertable.email='lead@mycomp.ru'), 0);
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Product details', '05.09.2016', '08.09.2016', 'Make technical description', (select id from usertable where usertable.email='vpt@mycomp.ru'), 0);
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('report mechanism', '01.09.2016', '03.09.2016', 'Create report engine', (select id from usertable where usertable.email='gr-2@mycomp.ru'), 0);
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('log report', '05.09.2016', '06.09.2016', 'Create log report class', (select id from usertable where usertable.email='gr-3@mycomp.ru'), 1);
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('attach loading', '04.09.2016', '07.09.2016', 'Create attach loading class', (select id from usertable where usertable.email='gr-1@mycomp.ru'), 2);
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Regress test', '06.09.2016', '07.09.2016', 'perform regress test', (select id from usertable where usertable.email='test1@mycomp.ru'), 2);