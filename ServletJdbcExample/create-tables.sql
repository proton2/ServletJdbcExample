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
	role_id varchar(64),
	CONSTRAINT usertablepk PRIMARY KEY (id)
);

create table if not exists public.worktask(
  id bigint not null default nextval('hibernate_sequence'::regclass),
  caption varchar(255),
  deadline timestamp without time zone,
  taskcontext varchar(255),
  taskdate timestamp without time zone,
  taskuser_id bigint,
  taskstatus_id varchar(64),
  CONSTRAINT worktaskpk PRIMARY KEY (id),
  CONSTRAINT fk_oam3n63solk5k7h80o8lqimrh FOREIGN KEY (taskuser_id)
      REFERENCES public.usertable (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table if not exists public.attach (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	filename varchar(255) UNIQUE,
	caption varchar(512),
	worktask_id bigint,
	CONSTRAINT attachpk PRIMARY KEY (id),
	CONSTRAINT fk_worktask_id FOREIGN KEY (worktask_id)
	REFERENCES public.worktask (id) MATCH SIMPLE
	    ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table if not exists public.WorkNote (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	caption varchar(255),
	noteDate timestamp without time zone,
	description varchar(512),
	model_id bigint,
	user_id bigint,
	CONSTRAINT worknotepk PRIMARY KEY (id),
	CONSTRAINT fk_model_id FOREIGN KEY (model_id)
	REFERENCES public.worktask (id) MATCH SIMPLE
    	ON UPDATE NO ACTION ON DELETE NO ACTION,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
    REFERENCES public.usertable (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('visitor', 'Vasili', 'Petrov', 'vtd@mycomp.ru', 'vtd', '111', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('guest', 'Sergey', 'Storogev', 'stx1@mycomp.ru', 'stx1', '222', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('product manager', 'Alexey', 'Vasin', 'vpt@mycomp.ru', 'vpt', '333', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('assistant', 'Elena', 'Kononova', 'ast@mycomp.ru', 'ast', '444', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('tester', 'Valentina', 'Sokolova', 'test1@mycomp.ru', 'test1', '555', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('tester', 'Tatiana', 'Smirnova', 'test2@mycomp.ru', 'test2', '666', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('admin', 'Stanislav', 'Dolgih', 'admin-gr@mycomp.ru', 'admin-gr', '777', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('programmer', 'Evgeniy', 'Stepanov', 'gr-1@mycomp.ru', 'gr-1', '888', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('programmer', 'Mihail', 'Vasilkov', 'gr-2@mycomp.ru', 'gr-2', '999', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('programmer', 'Oleg', 'Efremov', 'gr-3@mycomp.ru', 'gr-3', '101', 'user');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('lead', 'Vladimir', 'Ogarev', 'lead@mycomp.ru', 'lead', '102', 'boss');
insert into public.usertable (caption, firstname, lastname, email, login, password, role_id) values
('administrator', 'Admin', 'Admin', 'admin@mycomp.ru', 'root', 'root', 'admin');

insert into public.worktask (caption, taskdate, deadline, taskcontext, taskuser_id, taskstatus_id) values
('Test task', '04.09.2016', '05.09.2016', 'test current build', (select id from usertable where usertable.email='test2@mycomp.ru'), 'NEW');
insert into public.worktask (caption, taskdate, deadline, taskcontext, taskuser_id, taskstatus_id) values
('Test stend', '02.09.2016', '04.09.2016', 'Create test stend', (select id from usertable where usertable.email='admin-gr@mycomp.ru'), 'NEW');
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Plan description', '31.08.2016', '06.09.2016', 'Make technical description', (select id from usertable where usertable.email='lead@mycomp.ru'), 'NEW');
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Product details', '05.09.2016', '08.09.2016', 'Make technical description', (select id from usertable where usertable.email='vpt@mycomp.ru'), 'NEW');
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('report mechanism', '01.09.2016', '03.09.2016', 'Create report engine', (select id from usertable where usertable.email='gr-2@mycomp.ru'), 'NEW');
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('log report', '05.09.2016', '06.09.2016', 'Create log report class', (select id from usertable where usertable.email='gr-3@mycomp.ru'), 'CLOSED');
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('attach loading', '04.09.2016', '07.09.2016', 'Create attach loading class', (select id from usertable where usertable.email='gr-1@mycomp.ru'), 'ACTUAL');
insert into public.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Regress test', '06.09.2016', '07.09.2016', 'perform regress test', (select id from usertable where usertable.email='test1@mycomp.ru'), 'ACTUAL');

insert into public.worknote(caption, notedate, description, model_id, user_id) values
('my comment', '2016-11-26', 'note about this task', (select id from worktask wt where wt.caption = 'attach loading' and wt.taskcontext = 'Create attach loading class'), (select id from usertable where email = 'admin@mycomp.ru'));
insert into public.worknote(caption, notedate, description, model_id, user_id) values
('about dead line', '2016-11-27', 'thinking about something', (select id from worktask wt where wt.caption = 'attach loading' and wt.taskcontext = 'Create attach loading class'), (select id from usertable where email = 'admin@mycomp.ru'));
insert into public.worknote(caption, notedate, description, model_id, user_id) values
('task revision', '2016-11-27', 'some text about this task', (select id from worktask wt where wt.caption = 'attach loading' and wt.taskcontext = 'Create attach loading class'), (select id from usertable where email = 'lead@mycomp.ru'));