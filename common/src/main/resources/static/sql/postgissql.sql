﻿--  -- mysql由DELIMITER $$开头
-- CREATE OR REPLACE FUNCTION adffun(int4,varchar) -- mysql不能用or replace，入参要写类型和变量名，
-- RETURNS "varchar"
-- AS -- mysql没有AS
-- $BODY$
-- declare -- mysql的declare在begin内，且每次定义都要写，不能省
-- tempdata varchar;
-- ii integer;
-- begin
--  tempdata = (select name from point where gid =1);-- mysql不能直接赋值，要用set赋值
--  ii = 1;
--  II:=2;
-- RETURN ii;
-- end;
-- $BODY$
-- LANGUAGE 'plpgsql' VOLATILE;
-- ALTER FUNCTION adffun(int4,varchar) OWNER TO postgres;-- mysql无此句
-- SELECT adffun(2,'yong');