package cl.minsal.semantikos.model.snomedct;

import cl.minsal.semantikos.model.relationships.Target;

import java.sql.Timestamp;

/**
 * @author Andres Farias
 * @version 1.0
 * @created 17-ago-2016 12:52:05
 */
public class ConceptSCT implements Target {

	/** Identificador único (oficial) de Snomed CT para este concepto. */
	private long id;

	/** TODO: Determinar qué es esto */
	private Timestamp effectiveTime;

	/** Si el concepto Snomed CT está vigente */
	private boolean isActive;

	/** TODO: Averiguar qué es esto */
	private long moduleID;

	/** TODO: Averiguar qué es esto */
	private long definitionStatusID;

	public ConceptSCT(long id, Timestamp effectiveTime, boolean isActive, long moduleID, long definitionStatusID) {
		this.id = id;
		this.effectiveTime = effectiveTime;
		this.isActive = isActive;
		this.moduleID = moduleID;
		this.definitionStatusID = definitionStatusID;
	}

	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public long getModuleID() {
		return moduleID;
	}

	public long getDefinitionStatusID() {
		return definitionStatusID;
	}

	@Override
	public long getId() {
		return id;
	}
}