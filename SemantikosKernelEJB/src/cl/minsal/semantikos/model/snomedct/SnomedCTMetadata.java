package cl.minsal.semantikos.model.snomedct;

import cl.minsal.semantikos.model.DescriptionType;

/** Contiene algunas de las enumeraciones definidas en la jerarquía SNOMED CT Model Component (metadata)
 * (http://doc.ihtsdo.org/en_us/tig.html?t=trg2main_metahier)
 *
 * @author Diego Soto
 * @version 1.0
 * @created 28-sep-2016
 */

public enum SnomedCTMetadata {

    FSN(900000000000003001L),
    SYNONIM(900000000000013009L);

    private long id;

    SnomedCTMetadata(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
