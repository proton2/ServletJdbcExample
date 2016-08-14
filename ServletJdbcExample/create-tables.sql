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
	email character varying(255)
);

alter table public.usertable
add PRIMARY KEY (id);
