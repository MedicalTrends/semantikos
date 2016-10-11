package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.ws.response.CategoryResponse;

import javax.validation.constraints.NotNull;

/**
 * Created by Development on 2016-10-11.
 *
 */
public class CategoryMapper {

    public static CategoryResponse map(@NotNull Category category) {
        CategoryResponse res = new CategoryResponse();

        res.setColor(category.getColor());
        res.setId(category.getId());
        res.setName(category.getName());
        res.setNameAbbreviated(category.getNameAbbreviated());
        res.setRestriction(category.isRestriction());
        res.setValid(category.isValid());
        if ( category.getTagSemantikos() != null ) {
            res.setTagSMTKResponse(TagSMTKMapper.map(category.getTagSemantikos()));
        }

        return res;
    }

    public static Category map(CategoryResponse categoryResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
