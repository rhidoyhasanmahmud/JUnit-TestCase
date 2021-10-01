SET client_encoding = 'UTF8';
SET SCHEMA 'prj';

SET PRJ_ID '\'40288a8c7936e3bc01793ad3cb320003\'';
delete from task_finance where task in (select id from tasks where project_id = :PRJ_ID);
delete from task_comments where task_id in (select id from tasks where project_id = :PRJ_ID);
delete from task_assignee where task_id in (select id from tasks where project_id = :PRJ_ID);
delete from tasks where project_id = :PRJ_ID;

--delete from task_finance where task in (select id from tasks where project_id ='40288a8c7936e3bc01793ad3cb320003');
--delete from task_comments where task_id in (select id from tasks where project_id ='40288a8c7936e3bc01793ad3cb320003');
--delete from task_assignee where task_id in (select id from tasks where project_id ='40288a8c7936e3bc01793ad3cb320003');
--delete from tasks where project_id ='40288a8c7936e3bc01793ad3cb320003';

