CREATE SEQUENCE if not exists public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 55
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;

drop table if exists usertable;
create table if not exists public.usertable (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	caption varchar(255),
	firstname varchar(255),
	lastname varchar(255),
	email varchar(255),
	CONSTRAINT usertablepk PRIMARY KEY (id)
);

create table if not exists public.worktask(
  id bigint not null default nextval('hibernate_sequence'::regclass),
  caption varchar(255),
  deadline timestamp without time zone,
  taskcontext varchar(255),
  taskdate timestamp without time zone,
  taskuser_id bigint,
  CONSTRAINT worktaskpk PRIMARY KEY (id),
  CONSTRAINT fk_oam3n63solk5k7h80o8lqimrh FOREIGN KEY (taskuser_id)
      REFERENCES public.usertable (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);