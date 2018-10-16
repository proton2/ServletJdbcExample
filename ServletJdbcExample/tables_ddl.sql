--CREATE DATABASE servletjdbc WITH OWNER = postgres ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251' CONNECTION LIMIT = -1;

DROP SCHEMA IF exists dev cascade;
CREATE SCHEMA IF NOT EXISTS dev;

CREATE SEQUENCE if not exists dev.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 55
  CACHE 1;
ALTER TABLE dev.hibernate_sequence
  OWNER TO postgres;

create table if not exists dev.detail (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	notice varchar(255),
	CONSTRAINT detailpk PRIMARY KEY (id)
);

create table if not exists dev.usertable (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	caption varchar(255),
	creation_time timestamp,
	update_time timestamp,
	firstname varchar(255),
	lastname varchar(255),
	email varchar(255),
	login varchar(255) UNIQUE,
	password varchar(255),
	role_id varchar(64),
	detail_id bigint,
	CONSTRAINT usertablepk PRIMARY KEY (id),

	CONSTRAINT fk_detail_id_key FOREIGN KEY (detail_id)
      REFERENCES dev.detail (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table if not exists dev.worktask(
  id bigint not null default nextval('hibernate_sequence'::regclass),
  caption varchar(255),
  deadline timestamp without time zone,
  taskcontext varchar(255),
  taskdate timestamp without time zone,
  taskuser_id bigint,
  taskstatus_id varchar(64),
  CONSTRAINT worktaskpk PRIMARY KEY (id),
  CONSTRAINT fk_taskuser_id FOREIGN KEY (taskuser_id)
      REFERENCES dev.usertable (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table if not exists dev.attach (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	filename varchar(255) UNIQUE,
	caption varchar(512),
	worktask_id bigint,
	CONSTRAINT attachpk PRIMARY KEY (id),
	CONSTRAINT fk_worktask_id FOREIGN KEY (worktask_id)
	REFERENCES dev.worktask (id) MATCH SIMPLE
	    ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table if not exists dev.WorkNote (
	id bigint not null default nextval('hibernate_sequence'::regclass),
	caption varchar(255),
	noteDate timestamp without time zone,
	description varchar(512),
	model_id bigint,
	user_id bigint,
	CONSTRAINT worknotepk PRIMARY KEY (id),
	CONSTRAINT fk_model_id FOREIGN KEY (model_id)
	REFERENCES dev.worktask (id) MATCH SIMPLE
    	ON UPDATE NO ACTION ON DELETE NO ACTION,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
    REFERENCES dev.usertable (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

create or replace function dev.set_creation_time() returns trigger as $creation_time$
begin
    new.creation_time:=current_timestamp;
    return new;
end;
$creation_time$ language plpgsql;

create or replace function dev.set_update_time() returns trigger as $update_time$
begin
    new.update_time:=current_timestamp;
    return new;
end;
$update_time$ language plpgsql;

drop trigger if exists creation_time on dev.usertable;
create trigger creation_time before insert on dev.usertable
for each row execute procedure dev.set_creation_time();

drop trigger if exists update_time on dev.usertable;
create trigger update_time before update on dev.usertable
for each row execute procedure dev.set_update_time();