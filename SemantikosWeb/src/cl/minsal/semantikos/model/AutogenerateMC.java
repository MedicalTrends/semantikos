package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 18-10-16.
 */
public class AutogenerateMC {
    private List<String> sustancias;
    private List<String> ffa;
    private String volumen;
    private String unidadVolumen;

    public AutogenerateMC() {
        this.sustancias = new ArrayList<>();
        this.ffa = new ArrayList<>();
        this.volumen="";
        this.unidadVolumen="";
    }

    public List<String> getSustancias() {
        return sustancias;
    }

    public void setSustancias(List<String> sustancias) {
        this.sustancias = sustancias;
    }

    public List<String> getFfa() {
        return ffa;
    }

    public void setFfa(List<String> ffa) {
        this.ffa = ffa;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(Relationship relationship) {
        String vol= relationship.getTarget().toString();
        volumen= vol;
    }

    public String getUnidadVolumen() {
        return unidadVolumen;
    }

    public void setUnidadVolumen(RelationshipAttribute relationshipAttribute) {
        this.unidadVolumen = (((HelperTableRecord)relationshipAttribute.getTarget()).getValueColumn("description"));
    }

    public void addSustancia(Relationship relationship){
        String sustancia=((ConceptSMTK)relationship.getTarget()).getDescriptionFavorite().getTerm();
        String[] atributtes= new String[4];
        for (RelationshipAttribute relationshipAttribute: relationship.getRelationshipAttributes()) {
            if(relationshipAttribute.getRelationAttributeDefinition().getId()==8)atributtes[0]=" "+relationshipAttribute.getTarget().toString();
            if(relationshipAttribute.getRelationAttributeDefinition().getId()==9)atributtes[1]=(((HelperTableRecord)relationshipAttribute.getTarget()).getValueColumn("description"))+"/";
            if(relationshipAttribute.getRelationAttributeDefinition().getId()==10){
                if(Integer.parseInt(relationshipAttribute.getTarget().toString())>1){
                    atributtes[2]=relationshipAttribute.getTarget().toString();
                }else{
                    atributtes[2]="";
                }
            }
            if(relationshipAttribute.getRelationAttributeDefinition().getId()==11)atributtes[3]=(((HelperTableRecord)relationshipAttribute.getTarget()).getValueColumn("description"));
        }
        for (int i = 0; i < 4; i++) {
            sustancia= sustancia + atributtes[i];
        }
        sustancias.add(sustancia);

    }
    public void addFFA(Relationship relationship){
        ffa.add(((HelperTableRecord)relationship.getTarget()).getValueColumn("description")+"");
    }

    public void addVol(Relationship relationship){
        String vol= relationship.getTarget().toString();
        for (RelationshipAttribute relationshipAttribute: relationship.getRelationshipAttributes()) {
            if(relationshipAttribute.getRelationAttributeDefinition().getId()==12)vol=" "+(((HelperTableRecord)relationshipAttribute.getTarget()).getValueColumn("description"));
        }
        volumen= vol;
    }

    @Override
    public String toString() {
        String term="";
        for (int i = 0; i < sustancias.size(); i++) {
            if(i==0){
                term=sustancias.get(i);
            }else{
                term= term+" + "+ sustancias.get(i);
            }
        }
        term=" "+term;
        for (int i = 0; i < ffa.size(); i++) {

                term=term+" "+ffa.get(i);

        }
        term= term+" "+volumen+" " +unidadVolumen;

        return term;
    }
}
