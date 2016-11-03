CREATE OR REPLACE FUNCTION semantikos.add_user_profile(p_user_id int4, p_profile_id int4)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  INSERT INTO semantikos.smtk_user_profile (id_user, id_profile) VALUES (p_user_id, p_profile_id)
  RETURNING 1 into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;



CREATE OR REPLACE FUNCTION semantikos.create_user(p_username text, p_name text, p_last_name text, p_second_last_name text, p_email text, p_locked bool, p_failed_login_attempts int4, p_rut text)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  INSERT INTO semantikos.smtk_user (id, username         , name  , last_name  , second_last_name  , email , locked   , failed_login_attempts  , rut)
  VALUES (DEFAULT, p_username  , p_name, p_last_name, p_second_last_name, p_email, p_locked, p_failed_login_attempts, p_rut )
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;





CREATE OR REPLACE FUNCTION semantikos.delete_user_profiles(p_user_id int4)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  DELETE FROM semantikos.smtk_user u
  WHERE u.id = p_user_id
  RETURNING 1 into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;












CREATE OR REPLACE FUNCTION semantikos.get_profiles_by_user_id(user_id int8)
  RETURNS SETOF record
AS
$BODY$
begin
  RETURN QUERY select p.*
               from semantikos.smtk_profile p, semantikos.smtk_user_profile up
               where p.id = up.id_profile
                     and up.id_user = user_id ;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;







CREATE OR REPLACE FUNCTION semantikos.get_user_by_id(user_id int8)
  RETURNS SETOF record
AS
$BODY$
begin
  RETURN QUERY select u.id, u.username, u.password_hash, u.password_salt, u.name, u.last_name, u.second_last_name, u.email, u.locked, u.failed_login_attempts, u.last_login, u.last_password_change,
                 u.last_password_hash1, u.last_password_hash2, u.last_password_hash3, u.last_password_hash4, u.last_password_salt1, u.last_password_salt2, u.last_password_salt3, u.last_password_salt4,
                 u.rut
               from semantikos.smtk_user as u
               where u.id = user_id;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;





CREATE OR REPLACE FUNCTION semantikos.get_user_by_username(p_username text)
  RETURNS SETOF record
AS
$BODY$
begin
  RETURN QUERY select u.id, u.username, u.password_hash, u.password_salt, u.name, u.last_name, u.second_last_name, u.email, u.locked, u.failed_login_attempts, u.last_login, u.last_password_change,
                 u.last_password_hash1, u.last_password_hash2, u.last_password_hash3, u.last_password_hash4, u.last_password_salt1, u.last_password_salt2, u.last_password_salt3, u.last_password_salt4,
                 u.rut
               from semantikos.smtk_user as u
               where u.username = p_username;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;





CREATE OR REPLACE FUNCTION semantikos.update_user(p_name text, p_last_name text, p_second_last_name text, p_email text, p_rut text, p_user_id int4)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  UPDATE semantikos.smtk_user u SET
    u.name = p_name,
    u.last_name = p_name,
    u.second_last_name = p_second_last_name,
    u.email = p_email,
    u.rut= p_rut
  WHERE u.id = p_user_id
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;















CREATE OR REPLACE FUNCTION semantikos.update_user_passwords(
  p_date TIMESTAMP,
  p_password_hash text,
  p_last_password_hash1 text,
  p_last_password_hash2 text,
  p_last_password_hash3 text,
  p_last_password_hash4 text,
  p_password_salt text,
  p_last_password_salt1 text,
  p_last_password_salt2 text,
  p_last_password_salt3 text,
  p_last_password_salt4 text,
  p_user_id int4)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  UPDATE semantikos.smtk_user u SET
    u.last_password_change = p_date,
    u.password_hash = p_password_hash,
    u.last_password_hash1 = p_last_password_hash1,
    u.last_password_hash2 = p_last_password_hash2,
    u.last_password_hash3 = p_last_password_hash3,
    u.last_password_hash4 = p_last_password_hash4,
    u.password_salt = p_password_salt,
    u.last_password_salt1 = p_last_password_salt1,
    u.last_password_salt2 = p_last_password_salt2,
    u.last_password_salt3 = p_last_password_salt3,
    u.last_password_salt4 = p_last_password_salt4
  WHERE u.id = p_user_id
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;




















CREATE OR REPLACE FUNCTION semantikos.mark_login(p_date DATE, p_username TEXT)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  UPDATE semantikos.smtk_user u SET
    u.last_login = p_date
  WHERE u.username = p_username
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;



CREATE OR REPLACE FUNCTION semantikos.mark_login_fail( p_username TEXT)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  UPDATE semantikos.smtk_user u SET
    u.failed_login_attempts = u.failed_login_attempts +1
  WHERE u.username = p_username
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;


CREATE OR REPLACE FUNCTION semantikos.lock_user( p_username TEXT)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  UPDATE semantikos.smtk_user u SET
    u.locked = TRUE
  WHERE u.username = p_username
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;


CREATE OR REPLACE FUNCTION semantikos.unlock_user( p_username TEXT)
  RETURNS int8
AS
$BODY$
DECLARE result integer;
BEGIN

  UPDATE semantikos.smtk_user u SET
    u.locked = FALSE
  WHERE u.username = p_username
  RETURNING "id" into result;

  BEGIN
    exception when others then
    raise notice 'The transaction is in an uncommittable state. '
    'Transaction was rolled back';
    raise notice '% %', SQLERRM, SQLSTATE;

    RETURN -1;
  END;

  RETURN result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;



CREATE OR REPLACE FUNCTION semantikos.get_profile_by_id(profile_id int8)
  RETURNS SETOF record
AS
$BODY$
begin
  RETURN QUERY select p.*
               from semantikos.smtk_profile p
               where p.id = profile_id;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;