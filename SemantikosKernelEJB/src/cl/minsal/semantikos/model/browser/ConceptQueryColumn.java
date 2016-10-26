package cl.minsal.semantikos.model.browser;

import cl.minsal.semantikos.model.relationships.RelationshipDefinition;

import java.lang.reflect.Type;

/**
 * Created by root on 25-10-16.
 */
public class ConceptQueryColumn {

    String columnName;
    Sort sort;
    RelationshipDefinition relationshipDefinition;

    public ConceptQueryColumn(String columnName, Sort sort) {
        this.columnName = columnName;
        this.sort = sort;
    }

    public ConceptQueryColumn(String columnName, Sort sort, RelationshipDefinition relationshipDefinition) {
        this(columnName, sort);
        this.relationshipDefinition = relationshipDefinition;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}
