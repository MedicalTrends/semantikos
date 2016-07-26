package cl.minsal.semantikos.kernel.components;

import cl.minsal.semantikos.kernel.daos.ConceptDAO;
import cl.minsal.semantikos.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by stk-des01 on 01-06-16.
 */
@Stateless
public class ConceptManagerImpl implements ConceptManagerInterface {


    @EJB
    ConceptDAO conceptDAO;

    @EJB
    DescriptionManagerInterface descriptionManager;

    @EJB
    StateMachineManagerInterface stateMachineManager;

    /*
    @EJB
    StateManagerInterface stateManager;
    */

    @Override
    public ArrayList<Description> findDescriptionForPattern(String pattern) {


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

        return null;
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
    public ConceptSMTK newConcept(Category category, String term) {
        // Crear estado propuesto
        //ConceptStateMachine conceptStateMachine = conceptDAO.getConceptStateMachine();
        State propuesto = stateMachineManager.getConceptStateMachine().getInitialState();
        //propuesto.setStateMachine(conceptStateMachine);
        // Crear descriptor FSN
        Description fsn = new Description(term+" ("+category.getName()+")", descriptionManager.getTypeFSN());
        fsn.setCreationDate(Calendar.getInstance().getTime());
        fsn.setState(propuesto);
        // Crear descriptor preferido
        Description preferido = new Description(term, descriptionManager.getTypePreferido());
        preferido.setCreationDate(Calendar.getInstance().getTime());
        preferido.setState(propuesto);
        return new ConceptSMTK(category,fsn,preferido,propuesto);
    }

    @Override
    public List<ConceptSMTK> findConceptByPatternCategoryPageNumber(String Pattern, String[] category, int pageNumber, int pageSize) {

        Long[] states = {(long) 3,(long) 4};

        if (category != null) {
            if (category.length == 0) category = null;
        }

        if (Pattern != null) {
            if(Pattern.length()>=3){

                List<String> listPattern;
                listPattern=patternToList(Pattern);
                String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);

                return conceptDAO.getConceptByPatternCategory(arrPattern, category,pageNumber,pageSize,states);
            }

        }
        return conceptDAO.getConceptByPatternCategory(null,category,pageNumber,pageSize, states);

    }

    @Override
    public List<ConceptSMTK> findConceptByConceptIDOrDescriptionCategoryPageNumber(String patter, String[] categories, int pageNumber, int pageSize) {


        Long[] states = {(long) 3,(long) 4};


        if (categories != null) {
            if (categories.length == 0) categories = null;
        }

        if (patter != null) {
            patter=standardizationPattern(patter);
            if(patter.length()>=3){

                List<String> listPattern;
                listPattern=patternToList(patter);
                if(listPattern.size()==1){
                    return conceptDAO.getConceptByPatternOrConceptIDAndCategory(listPattern.get(0),categories,pageNumber,pageSize,states);
                }
                String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);

                return conceptDAO.getConceptByPatternCategory(arrPattern, categories,pageNumber,pageSize,states);
            }else{
                if(patter.length()>0){
                    List<String> listPattern;
                    listPattern=patternToList(patter);
                    if(listPattern.size()==1){
                        return conceptDAO.getConceptByPatternOrConceptIDAndCategory(listPattern.get(0),categories,pageNumber,pageSize,states);
                    }
                }

            }
        }
        return conceptDAO.getConceptByPatternCategory(null,categories,pageNumber,pageSize, states);
    }


    @Override
    public int getAllConceptCount(String Pattern, String[] category) {

        Long[] states = {(long) 3,(long) 4};


        if (category != null) {
            if (category.length == 0) category = null;
        }
        if (Pattern != null) {
            Pattern=standardizationPattern(Pattern);
            List<String> listPattern;
            listPattern=patternToList(Pattern);
            if(listPattern.size()==1){
                return conceptDAO.getCountFindConceptID(listPattern.get(0),category,states);
            }
            String[] arrPattern = listPattern.toArray(new String[listPattern.size()]);

            return conceptDAO.getAllConceptCount(arrPattern, category, states);
        }
        return conceptDAO.getAllConceptCount(null,category, states);

    }


    private List<String> patternToList(String pattern){
        StringTokenizer st;
        String token;
        st = new StringTokenizer(pattern, " ");
        ArrayList<String> listPattern = new ArrayList<String>();
        int count =0;


        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if(token.length()>=3){
                listPattern.add(token.trim());
            }
            if(count==0 && listPattern.size()==0){
                listPattern.add(token.trim());
            }
            count++;
        }
        return listPattern;
    }

    private String standardizationPattern(String pattern){

        pattern = Normalizer.normalize(pattern, Normalizer.Form.NFD);
        pattern = pattern.toLowerCase();
        pattern = pattern.replaceAll("[^\\p{ASCII}]", "");
        pattern = pattern.replaceAll("\\p{Punct}+", "");

        return pattern;

    }
}
