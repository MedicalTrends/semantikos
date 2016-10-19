package cl.minsal.semantikos.ws.mapping;

import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableColumn;
import cl.minsal.semantikos.ws.response.HelperTableColumnResponse;
import cl.minsal.semantikos.ws.response.HelperTableResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Development on 2016-10-19.
 *
 */
public class HelperTableMapper {

    public static HelperTableResponse map(HelperTable helperTable) {
        if ( helperTable != null ) {
            HelperTableResponse res = new HelperTableResponse();
            res.setId(helperTable.getId());
            res.setName(helperTable.getName());
            res.setDescription(helperTable.getDescription());
            res.setTablaName(helperTable.getTablaName());
            if ( helperTable.getColumns() != null ) {
                List<HelperTableColumnResponse> helperTableColumnResponses = new ArrayList<>();
                for (HelperTableColumn helperTableColumn : helperTable.getColumns()) {
                    helperTableColumnResponses.add(HelperTableColumnMapper.map(helperTableColumn));
                }
                res.setColumns(helperTableColumnResponses);
            }
            return res;
        }
        return null;
    }

}
