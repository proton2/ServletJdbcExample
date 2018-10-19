@echo off
REM Copyright (c) 2012-2015, EnterpriseDB Corporation.  All rights reserved

REM PostgreSQL server psql runner script for Windows

SET server=localhost
SET port=5432
SET database=servletjdbc
SET username=postgres
set PGPASSWORD=postgres

REM psql
psql -h %server% -U %username% -d %database% -p %port% -f tables_ddl.sql
psql -h %server% -U %username% -d %database% -p %port% -f insert_data.sql