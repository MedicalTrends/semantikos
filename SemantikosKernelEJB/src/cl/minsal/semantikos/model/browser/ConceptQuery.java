package cl.minsal.semantikos.model.browser;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.Tag;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Target;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQuery {

    private String query;
    private List<ConceptQueryFilter> filters;

    private Boolean toBeReviewed;
    private Boolean toBeConsulted;

    private Boolean modeled;

    private Tag tag;

    private Date creationDateSince;
    private Date creationDateTo;

    private int pageSize;
    private int pageNumber;

    private String order;

    private List<Category> categories;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<ConceptQueryFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ConceptQueryFilter> filters) {
        this.filters = filters;
    }

    public Boolean getToBeReviewed() {
        return toBeReviewed;
    }

    public void setToBeReviewed(Boolean toBeReviewed) {
        this.toBeReviewed = toBeReviewed;
    }

    public Boolean getToBeConsulted() {
        return toBeConsulted;
    }

    public void setToBeConsulted(Boolean toBeConsulted) {
        this.toBeConsulted = toBeConsulted;
    }

    public Boolean getModeled() {
        return modeled;
    }

    public void setModeled(Boolean modeled) {
        this.modeled = modeled;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Date getCreationDateSince() {
        return creationDateSince;
    }

    public void setCreationDateSince(Date creationDateSince) {
        this.creationDateSince = creationDateSince;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Long[] getCategoryValues(){

        List<Long> categoryValues = new ArrayList<>();

        for (Category category : getCategories())
            categoryValues.add(category.getId());

        if(categoryValues.isEmpty())
            return null;

        else {
            Long[] array = new Long[categoryValues.size()];
            return categoryValues.toArray(array);
        }
    }

    public Long[] getHelperTableValues(){

        List<Long> helperTableValues = new ArrayList<>();

        for (ConceptQueryFilter filter : filters)
            helperTableValues.addAll(filter.getHelperTableValues());

        if(helperTableValues.isEmpty())
            return null;

        else {
            Long[] array = new Long[helperTableValues.size()];
            return helperTableValues.toArray(array);
        }
    }

    public String[] getBasicTypeValues(){

        List<String> basicTypeValues = new ArrayList<>();

        for (ConceptQueryFilter filter : filters)
            basicTypeValues.addAll(filter.getBasicTypeValues());

        if(basicTypeValues.isEmpty())
            return null;

        else {
            String[] array = new String[basicTypeValues.size()];
            return basicTypeValues.toArray(array);
        }
    }

    public List<ConceptQueryParameter> getConceptQueryParameters(){

        List<ConceptQueryParameter> conceptQueryParameters = new ArrayList<>();

        conceptQueryParameters.add(new ConceptQueryParameter(Long.class, getCategoryValues(), true));


        conceptQueryParameter.put(Long.class, getCategoryValues()[0]);
        conceptQueryParameters.add(conceptQueryParameter);

        conceptQueryParameter.get

        conceptQueryParameter.put(Long.class, getCategoryValues()[0]);
        conceptQueryParameters.add(conceptQueryParameter);

        return map;
    }
}
