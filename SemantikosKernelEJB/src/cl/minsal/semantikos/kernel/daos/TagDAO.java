package cl.minsal.semantikos.kernel.daos;

import cl.minsal.semantikos.model.Tag;

import java.util.List;

/**
 * @author Gustavo Punucura
 */
public interface TagDAO {


    /**
     * Este metodo es el encargado de persistir el tag en la base de datos
     * @param tag
     */
    public void persist(Tag tag);

    /**
     * Este metodo es el encargado de actualizar la informacion de un tag ya existente en la base de datos
     * @param tag
     */
    public void update(Tag tag);

    /**
     * Este metodo es el encargado remover un tag este tag si contiene hijos, tambien deben ser eliminados
     * @param tag
     */
    public void remove(Tag tag);


    /**
     * Este metodo es el encargado de buscar etiquetas por nombre
     * @param NameTag
     * @return
     */
    public List<Tag> findTagBy(String NameTag);



}
