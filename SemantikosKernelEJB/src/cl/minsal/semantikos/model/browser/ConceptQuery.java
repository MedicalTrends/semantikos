package cl.minsal.semantikos.model.browser;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.model.Tag;

import java.util.Date;
import java.util.List;

/**
 * Created by BluePrints Developer on 21-09-2016.
 */
public class ConceptQuery {

    private String query;
    private List<ConceptQueryFilter> filters;

    private Boolean toBeReviewed;
    private Boolean toBeConsulted;

    private Boolean modeled;



    private Integer tagId;

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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
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
}
