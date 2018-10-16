insert into dev.detail (notice) values ('value 1');
insert into dev.detail (notice) values ('value 2');
insert into dev.detail (notice) values ('value 3');

insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('visitor', 'Vasili', 'Petrov', 'vtd@mycomp.ru', 'vtd', '111', 'user', (select id from dev.detail where notice = 'value 1' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('guest', 'Sergey', 'Storogev', 'stx1@mycomp.ru', 'stx1', '222', 'user', (select id from dev.detail where notice = 'value 2' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('product manager', 'Alexey', 'Vasin', 'vpt@mycomp.ru', 'vpt', '333', 'user', (select id from dev.detail where notice = 'value 3' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('assistant', 'Elena', 'Kononova', 'ast@mycomp.ru', 'ast', '444', 'user', (select id from dev.detail where notice = 'value 1' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('tester', 'Valentina', 'Sokolova', 'test1@mycomp.ru', 'test1', '555', 'user', (select id from dev.detail where notice = 'value 2' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('tester', 'Tatiana', 'Smirnova', 'test2@mycomp.ru', 'test2', '666', 'user', (select id from dev.detail where notice = 'value 3' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('admin', 'Stanislav', 'Dolgih', 'admin-gr@mycomp.ru', 'admin-gr', '777', 'user', (select id from dev.detail where notice = 'value 1' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('programmer', 'Evgeniy', 'Stepanov', 'gr-1@mycomp.ru', 'gr-1', '888', 'user', (select id from dev.detail where notice = 'value 2' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('programmer', 'Mihail', 'Vasilkov', 'gr-2@mycomp.ru', 'gr-2', '999', 'user', (select id from dev.detail where notice = 'value 3' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('programmer', 'Oleg', 'Efremov', 'gr-3@mycomp.ru', 'gr-3', '101', 'user', (select id from dev.detail where notice = 'value 1' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('lead', 'Vladimir', 'Ogarev', 'lead@mycomp.ru', 'lead', '102', 'boss', (select id from dev.detail where notice = 'value 2' limit 1)) on conflict do nothing;
insert into dev.usertable (caption, firstname, lastname, email, login, password, role_id, detail_id) values
('administrator', 'Admin', 'Admin', 'admin@mycomp.ru', 'root', 'root', 'admin', (select id from dev.detail where notice = 'value 3' limit 1)) on conflict do nothing;

insert into dev.worktask (caption, taskdate, deadline, taskcontext, taskuser_id, taskstatus_id) values
('Test task', '04.09.2016', '05.09.2016', 'test current build', (select id from dev.usertable where usertable.email='test2@mycomp.ru' limit 1), 'NEW');
insert into dev.worktask (caption, taskdate, deadline, taskcontext, taskuser_id, taskstatus_id) values
('Test stend', '02.09.2016', '04.09.2016', 'Create test stend', (select id from dev.usertable where usertable.email='admin-gr@mycomp.ru' limit 1), 'NEW');
insert into dev.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Plan description', '31.08.2016', '06.09.2016', 'Make technical description', (select id from dev.usertable where usertable.email='lead@mycomp.ru' limit 1), 'NEW');
insert into dev.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Product details', '05.09.2016', '08.09.2016', 'Make technical description', (select id from dev.usertable where usertable.email='vpt@mycomp.ru' limit 1), 'NEW');
insert into dev.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('report mechanism', '01.09.2016', '03.09.2016', 'Create report engine', (select id from dev.usertable where usertable.email='gr-2@mycomp.ru' limit 1), 'NEW');
insert into dev.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('log report', '05.09.2016', '06.09.2016', 'Create log report class', (select id from dev.usertable where usertable.email='gr-3@mycomp.ru' limit 1), 'CLOSED');
insert into dev.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('attach loading', '04.09.2016', '07.09.2016', 'Create attach loading class', (select id from dev.usertable where usertable.email='gr-1@mycomp.ru' limit 1), 'ACTUAL');
insert into dev.worktask (caption, taskdate, deadline, taskcontext,  taskuser_id, taskstatus_id) values
('Regress test', '06.09.2016', '07.09.2016', 'perform regress test', (select id from dev.usertable where usertable.email='test1@mycomp.ru' limit 1), 'ACTUAL');

insert into dev.worknote(caption, notedate, description, model_id, user_id) values
('my comment', '2016-11-26', 'note about this task', (select id from dev.worktask wt where wt.caption = 'attach loading' and wt.taskcontext = 'Create attach loading class' limit 1), (select id from dev.usertable where email = 'admin@mycomp.ru' limit 1));
insert into dev.worknote(caption, notedate, description, model_id, user_id) values
('about dead line', '2016-11-27', 'thinking about something', (select id from dev.worktask wt where wt.caption = 'attach loading' and wt.taskcontext = 'Create attach loading class' limit 1), (select id from dev.usertable where email = 'admin@mycomp.ru' limit 1));
insert into dev.worknote(caption, notedate, description, model_id, user_id) values
('task revision', '2016-11-27', 'some text about this task', (select id from dev.worktask wt where wt.caption = 'attach loading' and wt.taskcontext = 'Create attach loading class' limit 1), (select id from dev.usertable where email = 'lead@mycomp.ru' limit 1));