package cl.minsal.semantikos.model.helpertables;

import java.util.*;

/**
 * Esta enumeración almacena todas las tablas auxiliares existentes.
 */
public class HelperTableFactory {

    /** Tabla Auxiliar ATC */
    private HelperTable atcHT;

    /** Tabla Auxiliar DCI */
    private HelperTable dciHT;

    /** Tabla Auxiliar Recomenraciones */
    private HelperTable recommentationsHT;

    /** Tabla Auxiliar Tipo Recomendaciones */
    private HelperTable recommendationTypeHT;



    private Map<String, HelperTable> helperTablesByTableName = new HashMap<>();

    private static final HelperTableFactory singletonInstance = new HelperTableFactory();

    private HelperTableFactory() {
        createHelperTables();
    }

    private void createHelperTables() {

        HelperTable atcHelperTable = createATC();
        HelperTable dciHelperTable = createDCI();

        HelperTable rec = createRecommendation();
        HelperTable rectype = createRecommendationType();

        helperTablesByTableName.put(atcHelperTable.getTablaName(), atcHelperTable);
        helperTablesByTableName.put(dciHelperTable.getTablaName(), dciHelperTable);
        helperTablesByTableName.put(rec.getTablaName(), rec);
        helperTablesByTableName.put(rectype.getTablaName(), rectype);
    }

    public Collection<HelperTable> getHelperTablesByTableName() {
        return helperTablesByTableName.values();
    }

    /**
     * Este método es responsable de crear una Tabla Auxiliar ATC.
     *
     * @return Una tabla ATC.
     */
    private HelperTable createATC() {
        HelperTableColumn idColumn = new HelperTableColumn("id", true, false, true);
        HelperTableColumn codigoATCColumn = new HelperTableColumn("codigo_atc", false, true, true);
        HelperTableColumn descripcionATCColumn = new HelperTableColumn("descripcion_atc", false, true, true);

        HelperTableColumn[] columns = {idColumn, codigoATCColumn, descripcionATCColumn};
        this.atcHT = new HelperTable((long) 1, "ATC", "Tabla de códigos ATC", "helper_table_atc", Arrays.asList(columns));

        return this.atcHT;
    }



    /**
     * Este método es responsable de crear una Tabla Auxiliar Recomendaciones.
     *
     * @return Una tabla recomendaciones.
     */
    private HelperTable createRecommendation() {
        HelperTableColumn idColumn = new HelperTableColumn("id", true, false, true);
        HelperTableColumn descriptionColumn = new HelperTableColumn("descripcion", false, true, true);
        HelperTableColumn creation_dateColumn = new HelperTableColumn("fecha_registro", false, false, true);
        HelperTableColumn user_registerColumn = new HelperTableColumn("usuario_registro", false, false, true);
        HelperTableColumn is_validColumn = new HelperTableColumn("valid", false, false, true);


        HelperTableColumn[] columns = {idColumn, descriptionColumn, creation_dateColumn, user_registerColumn, is_validColumn};
        this.recommentationsHT = new HelperTable((long) 3, "Recomendaciones", "Tabla de Recomendaciones", "smtk_ht_recommendation", Arrays.asList(columns));

        return this.recommentationsHT;
    }

    /**
     * Este método es responsable de crear una Tabla Auxiliar Tipo Recomendaciones.
     *
     * @return Una tabla Tipo Recomendaciones.
     */
    private HelperTable createRecommendationType() {
        HelperTableColumn idColumn = new HelperTableColumn("id", true, false, true);
        HelperTableColumn descriptionColumn = new HelperTableColumn("name", false, true, true);


        HelperTableColumn[] columns = {idColumn, descriptionColumn};
        this.recommendationTypeHT = new HelperTable((long) 4, "Tipo Recomendaciones", "Tabla de Tipo de Recomendaciones", "smtk_ht_recommendation_type", Arrays.asList(columns));

        return this.recommendationTypeHT;
    }






    /**
     * Este método es responsable de crear una Tabla Auxiliar ATC.
     *
     * @return Una tabla ATC.
     */
    private HelperTable createDCI() {
        HelperTableColumn idColumn = new HelperTableColumn("id", true, false, true);
        HelperTableColumn descriptionColumn = new HelperTableColumn("description", false, true, true);
        HelperTableColumn creation_dateColumn = new HelperTableColumn("creation_date", false, true, true);
        HelperTableColumn user_registerColumn = new HelperTableColumn("user_register", false, true, true);
        HelperTableColumn is_validColumn = new HelperTableColumn("is_valid", false, true, true);
        HelperTableColumn delete_dateColumn = new HelperTableColumn("delete_date", false, true, true);

        HelperTableColumn[] columns = {idColumn, descriptionColumn, creation_dateColumn, user_registerColumn, is_validColumn, delete_dateColumn};
        this.dciHT = new HelperTable((long) 2, "DCI", "Tabla de Denominaciones Comunes Internacionales (DCI)", "helper_table_dci", Arrays.asList(columns));

        return this.dciHT;
    }

    public HelperTable getHelperTableATC() {
        return atcHT;
    }

    public HelperTable getHelperTableDCI() {
        return dciHT;
    }

    public HelperTable getHelperTableRecommendation() {
        return recommentationsHT;
    }

    public HelperTable getHelperTableRecommendationType() {
        return recommendationTypeHT;
    }

    public static HelperTableFactory getInstance() {
        return singletonInstance;
    }

    /**
     * Este método es responsable de retornar la tabla auxiliar cuyo ID coincide con el parámetro.
     *
     * @param idHelperTable Identificador único de la tabla auxiliar que se desea recuperar.
     *
     * @return Una instancia de la tabla.
     *
     * @throws java.lang.IllegalArgumentException Si no existe una tabla con dicho ID.
     */
    public HelperTable getHelperTable(long idHelperTable) throws IllegalArgumentException {

        switch ((int) idHelperTable) {
            case 1:
                return this.atcHT;

            case 2:
                return this.dciHT;

            case 3:
                return this.dciHT;

            case 4:
                return this.dciHT;
        }

        throw new IllegalArgumentException("No existe tabla auxiliar con ID = " + idHelperTable);
    }

    /**
     * Este método es responsable de retornar una Tabla Auxiliar a partir de su nombre.
     * @param tableName El nombre de la tabla auxiliar que se desea recuperar.
     * @return La tabla auxiliar de nombre <code>tableName</code>.
     */
    public HelperTable getHelperTable(String tableName) {
        if (this.helperTablesByTableName.containsKey(tableName)) {
            return this.helperTablesByTableName.get(tableName);
        }

        throw new IllegalArgumentException("No existe tabla auxiliar de nombre '" + tableName + ".");
    }
}