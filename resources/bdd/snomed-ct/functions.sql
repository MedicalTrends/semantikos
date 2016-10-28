-- Function: semantikos.get_concepts_sct_by_pattern_id(character varying)

-- DROP FUNCTION semantikos.get_concepts_sct_by_pattern_id(character varying);

CREATE OR REPLACE FUNCTION semantikos.get_concepts_sct_by_pattern_id(IN c_id character varying)
  RETURNS TABLE(id bigint, active boolean, effective_time timestamp without time zone, id_module bigint, id_definition_status bigint) AS
$BODY$
BEGIN
  RETURN QUERY SELECT id, active, effective_time, id_module, id_definition_status
  FROM semantikos.smtk_sct_concept_file c
  WHERE cast(c.id as text) like '%'||c_id||'%'; 
  
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION semantikos.get_concepts_sct_by_pattern_id(character varying)
  OWNER TO postgres;
