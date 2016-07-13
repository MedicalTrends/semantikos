package cl.minsal.semantikos.kernel;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Description;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by stk-des01 on 01-06-16.
 */
@Stateless
public class ConceptManagerImpl implements ConceptManagerInterface {

    String driver = "org.postgresql.Driver";
    String ruta = "jdbc:postgresql://192.168.0.221:5432/postgres";
    String user = "postgres";
    String password = "1q2w3e";

    @Override
    public ArrayList<Description> findDescriptionForPattern(String pattern) {

        ArrayList<Description> descriptions = new ArrayList<Description>();


/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call find_description(?,?)}");

            call.setString(1, "");
            call.setString(2, pattern);
            call.execute();

            ResultSet rs = call.getResultSet();

            int idD;
            String description;

            while (rs.next()) {
                idD = Integer.parseInt(rs.getString("id_concepto"));
                description = rs.getString("term");
                descriptions.add(new Description(idD, description));
            }
            conne.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

        return descriptions;
    }

    @Override
    public String addConcept(String idCategory, boolean isValid) {
        String idConcepto=null;



/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call crea_concepto(?,?)}");

            call.setInt(1, Integer.parseInt(idCategory));
            call.setBoolean(2, isValid);

            call.execute();


            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                idConcepto = rs.getString(1);

            }
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {

            System.out.println(e.toString());
        }
*/

        return idConcepto;
    }

    @Override
    public int getIDConceptBy(int idDescription) {

        int idDes=0;

/*
        try {
            Class.forName(driver);
            Connection conne = (Connection) DriverManager.getConnection(ruta, user, password);
            CallableStatement call = conne.prepareCall("{call get_concept_by_description_id(?)}");

            call.setInt(1, idDescription);
            call.execute();

            ResultSet rs = call.getResultSet();
            while (rs.next()) {
                idDes = Integer.parseInt(rs.getString(1));
            }
            conne.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
*/

        return idDes;
    }

    @Override
    public ConceptSMTK newConcept(int idCategory, String termino) {

        //ConceptSMTK conceptSMTK = new ConceptSMTK(idCategory, termino);
        return null;
    }

    @Override
    public List<ConceptSMTK> findConceptByPatternCategoryPageNumber(String Pattern, String[] category, int pageNumber, int pageSize) {

        /*
        DAOConceptImpl concept = new DAOConceptImpl();

        if (category != null) {
            if (category.length == 0) category = null;
        }

        if (Pattern != null) {
            List<String> listPattern;
            listPattern=patternToList(Pattern);
            String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);

            return concept.getConceptBy(arrPattern, category,pageNumber,pageSize);

        }
        return concept.getConceptBy(null,category,pageNumber,pageSize);
        */
        return null;

    }


    @Override
    public int getAllConceptCount(String Pattern, String[] category) {

        /*
        DAOConceptImpl concept= new DAOConceptImpl();

        if (category != null) {
            if (category.length == 0) category = null;
        }
        if (Pattern != null) {
            List<String> listPattern;
            listPattern=patternToList(Pattern);
            String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);
            return concept.getAllConceptCount(arrPattern, category);

        }
        return concept.getAllConceptCount(null,category);
        */

        return 0;
    }


    private List<String> patternToList(String pattern){
        StringTokenizer st;
        String token;
        st = new StringTokenizer(pattern, " ");
        ArrayList<String> listPattern = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            token = st.nextToken();
            listPattern.add(token);
        }
        return listPattern;
    }
}
