package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.Category;
import cl.minsal.semantikos.ws.response.CategoryResponse;

/**
 * Created by Development on 2016-10-11.
 *
 */
public class CategoryMapper {

    public static CategoryResponse map(Category category) {
        if ( category != null ) {
            CategoryResponse res = new CategoryResponse();

            res.setId(category.getId());
            res.setName(category.getName());
            res.setNameAbbreviated(category.getNameAbbreviated());
            res.setRestriction(category.isRestriction());
            res.setValid(category.isValid());
            if (category.getTagSemantikos() != null) {
                res.setTagSMTKResponse(TagSMTKMapper.map(category.getTagSemantikos()));
            }

            return res;
        } else {
            return null;
        }
    }

    public static Category map(CategoryResponse categoryResponse) {
        throw new IllegalStateException("Not implemented yet");
    }

}
