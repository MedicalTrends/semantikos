package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.ConceptQueryDAO;
import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.browser.ConceptQuery;
import cl.minsal.semantikos.model.browser.ConceptQueryFilter;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
@Stateless
public class ConceptQueryManagerImpl implements ConceptQueryManager{


    @EJB
    ConceptQueryDAO conceptQueryDAO;

    @Override
    public ConceptQuery getDefaultQueryByCategory(Category category) {
        //TODO: implementar de veldá

        ConceptQuery query = new ConceptQuery();

        List<Category> categories = new ArrayList<Category>();
        categories.add(category);
        query.setCategories(categories);

        List<ConceptQueryFilter> filters = new ArrayList<ConceptQueryFilter>();
        query.setFilters(filters);


        fillFiltersByCategory(category);

        return query;
    }

    private void fillFiltersByCategory(Category category) {

        switch ( (int)category.getId()) {
            case 3:  return;  //Insumos
            case 4:  return;  //Dispositivos Medicos
            case 7:  return;  //Calificador
            case 8:  return;  //Ambiente o localización geográfica
            case 9:  return;  //Areas institucionales
            case 10:  return;  //Concepto Especial
            case 11:  return;  //Contexto Social
            case 12:  return;  //Elemento de Registro
            case 13:  return;  //Fármacos - Sustancia
            case 14:  return;  //Entidad Observable
            case 16:  return;  //Especialidad
            case 17:  return;  //Espécimen
            case 18:  return;  //Estadificación y Escalas
            case 19:  return;  //Estructura Corporal
            case 20:  return;  //Evento
            case 21:  return;  //Fuerza Física
            case 22:  return;  //Objeto Físico
            case 23:  return;  //Organismo
            case 24:  return;  //Problemas
            case 25:  return;  //Profesión
            case 26:  return;  //Subespecialidad
            case 27:  return;  //Procedimientos
            case 28:  return;  //Indicaciones de Laboratorio
            case 29:  return;  //Interconsultas
            case 30:  return;  //Indicaciones Generales
            case 31:  return;  //Imagenología
            case 33:  return;  //Fármacos - Medicamento Básico
            case 34:  return;  //Fármacos - Medicamento Clínico
            case 35:  return;  //Fármacos - Medicamento Clínico con Envase
            case 36:  return;  //Fármacos - Grupo de Familia de Producto
            case 37:  return;  //Fármacos - Familia de Productos
            case 38:  return;  //Fármacos - Producto Comercial
            case 39:  return;  //Fármacos - Producto Comercial con Envase




        }


    }

    @Override
    public List<ConceptSMTK> executeQuery(ConceptQuery query) {

        return conceptQueryDAO.callQuery(query);

    }

    @Override
    public List<RelationshipDefinition> getShowableAttributesByCategory(Category category) {
//TODO:implement
        return new ArrayList<RelationshipDefinition>();
    }
}
