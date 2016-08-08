package cl.minsal.semantikos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrés Farías
 */
public class DescriptionTypeFactory {

    private static final DescriptionTypeFactory instance = new DescriptionTypeFactory();

    public static final DescriptionType TYPELESS_DESCRIPTION_TYPE = new DescriptionType(-1, "Sin Tipo", "El tipo de descripcion sin tipo :).");

    public static final String FAVOURITE_DESCRIPTION_TYPE_NAME = "Preferida";

    private Map<String, DescriptionType> descriptionTypes;

    /**
     * Constructor privado para el Singleton del Factory.
     */
    private DescriptionTypeFactory() {
        this.descriptionTypes = new HashMap();
    }

    public static DescriptionTypeFactory getInstance() {
        return instance;
    }

    public DescriptionType getTypelessDescriptionType() {
        return TYPELESS_DESCRIPTION_TYPE;
    }

    /**
     * Este método es responsable de retornar el tipo de descripción llamado FSN.
     *
     * @return Retorna una instancia de FSN.
     */
    public DescriptionType getFSNDescriptionType() {
        return this.descriptionTypes.get("FSN");
    }

    public DescriptionType getFavoriteDescriptionType() {
        if (descriptionTypes.containsKey(FAVOURITE_DESCRIPTION_TYPE_NAME)) {
            return this.descriptionTypes.get(FAVOURITE_DESCRIPTION_TYPE_NAME.toUpperCase());
        } else {
            return new DescriptionType(-1, FAVOURITE_DESCRIPTION_TYPE_NAME, "Descripción Preferida (por defecto)");
        }
    }

    public void setDescriptionTypes(Map<String, DescriptionType> descriptionTypes) {
        this.descriptionTypes = descriptionTypes;
    }

    public List<DescriptionType> getDescriptionTypes() {
        return new ArrayList<>(descriptionTypes.values());
    }
}
