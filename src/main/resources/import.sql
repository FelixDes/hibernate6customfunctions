-- Default data
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Jonn', 100, 'Adams');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Laurel', 200, 'Mitchell');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Hadwin', 300, 'Potter');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Stewart', 300, 'Robson');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Jonn', 350, 'Robson');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Joey', 400, 'Barton');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Bert', 500, 'Marshall');
insert into Employee (id, name, salary, surname) values (gen_random_uuid(), 'Gavin', 500, 'Houle');

-- Function that returns second max salary of Employee table
create or replace function secondMaxSalary() returns bigint as $$ select max(salary) from Employee where salary <> (select max(salary) from Employee) $$ language SQL;

-- Aggregate function that returns number of rows greater than 200
create or replace function greater_than(c bigint, val numeric, gr_val numeric) returns bigint as $$ begin return case when val > gr_val then (c + 1)::bigint else c::bigint end; end; $$ language "plpgsql";
create or replace function agg_final(c bigint) returns bigint as $$ begin return c; end; $$ language "plpgsql";
create or replace aggregate countItemsGreaterVal(numeric, numeric) (sfunc = greater_than(bigint, numeric, numeric), stype = bigint, finalfunc = agg_final, initcond = 0);