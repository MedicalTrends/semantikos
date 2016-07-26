package cl.minsal.semantikos.model;

import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * @author Andres Farias
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BasicTypeDefinition.class, name = "BasicTypeDefinition")

        //@JsonSubTypes.Type(value = Cat.class, name = "Cat")
}
)
public abstract class TargetDefinition {


}
