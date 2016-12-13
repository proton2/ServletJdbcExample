# ServletJdbcExample

I create this training project for learning the basics of Java EE - servlet engine and JDBC (no more frameworks).
The purpose of the project - to understand how the servlets and JDBC working.

Small Web application - work tasks and deadlines. Like a little Jira.

- Only Java Servlets API and Java JDBC without any frameworks (to study how Servlets and jdbc working). Using jsp;
- Dao factory template to easy working with many dao;
- Use caching (use ehcache provider);
- Separation reading light objects for list froms and reading usual entities. Differents DAO;
- Load SQL querries for dao from external xml file (to avoid changes in the source code if necessary change SQL querry);
- authorization by password and different access rights depending on the user role;
- pagination (portion objects loading per each page);
- import list of main objects from excel format to database;
- jQuerry elements in interface (use tabs);
- mechanism for loading and storage attach files to tasks;
- Comments for tasks;
- Logging into separatly log files;

proton2@mail.ru

![List worktasks](https://github.com/proton2/ServletJdbcExample/tree/master/ServletJdbcExample/img/sje-01.jpg)