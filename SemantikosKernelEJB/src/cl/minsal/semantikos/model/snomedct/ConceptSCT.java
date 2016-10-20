package cl.minsal.semantikos.model.snomedct;

import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetType;

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
	private long moduleId;

	/** TODO: Averiguar qué es esto */
	private long definitionStatusId;

	public ConceptSCT() {
	}

	public ConceptSCT(long id, Timestamp effectiveTime, boolean isActive, long moduleId, long definitionStatusId) {
		this.id = id;
		this.effectiveTime = effectiveTime;
		this.isActive = isActive;
		this.moduleId = moduleId;
		this.definitionStatusId = definitionStatusId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public long getDefinitionStatusId() {
		return definitionStatusId;
	}

	public void setDefinitionStatusId(long definitionStatusId) {
		this.definitionStatusId = definitionStatusId;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public TargetType getTargetType() {
		return TargetType.SnomedCT;
	}

	@Override
	public Target copy() {
		return new ConceptSCT();
	}

}