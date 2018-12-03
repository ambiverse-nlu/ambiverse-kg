package de.mpg.mpi_inf.ambiversenlu.kg.dao;

import de.mpg.mpi_inf.ambiversenlu.kg.model.Category;
import de.mpg.mpi_inf.ambiversenlu.kg.model.EntitiesSearchRequest;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Entity;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
import java.util.Map;

/**
 * Created by dmilchev on 10/14/15.
 */
@Contract
public interface IDao {

    Map<String, Entity> getEntityMetadataByKbId(List<String> ids) throws Exception;

    Map<String, Category> getCategoryMetadataByKbId(List<String> ids) throws Exception;

    Meta getEntitiesMeta() throws Exception;

    Meta getCategoriesMeta() throws Exception;

    List<String> searchEntities(EntitiesSearchRequest entitiesSearchRequest) throws Exception;

//    Map<String, Float> searchTypes(SearchKbEntries entities) throws Exception;

}
