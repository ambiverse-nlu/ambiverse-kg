package de.mpg.mpi_inf.ambiversenlu.kg.resource.impl.mocks;

import de.mpg.mpi_inf.ambiversenlu.kg.dao.IDao;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Category;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Entity;
import de.mpg.mpi_inf.ambiversenlu.kg.model.Meta;

import java.util.Map;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public abstract class MockedIDaoFactory implements Supplier<IDao> {

  @Override
  public IDao get() {
    final IDao mockedDAO = mock(IDao.class);

    try {
      Map<String, Entity> entities = getEntityMetadataByKbId();
      if (entities == null) {
        when(mockedDAO.getEntityMetadataByKbId(anyList())).thenThrow(new Exception("Non-mocked method getEntityMetadataByKbId invoked"));
      } else {
        when(mockedDAO.getEntityMetadataByKbId(anyList())).thenAnswer(invocation -> entities);
      }

      Map<String, Category> types = getCategoryMetadataByKbId();
      if (types == null) {
        when(mockedDAO.getCategoryMetadataByKbId(anyList())).thenThrow(new Exception("Non-mocked method getCategoryMetadataByKbId invoked"));
      } else {
        when(mockedDAO.getCategoryMetadataByKbId(anyList())).thenAnswer(invocationOnMock -> types);
      }

      Meta entitiesMeta = getMeta();
      if (entitiesMeta == null) {
        when(mockedDAO.getEntitiesMeta()).thenThrow(new Exception("Non-mocked method getEntitiesMeta invoked"));
      } else {
        when(mockedDAO.getEntitiesMeta()).thenAnswer(invocationOnMock -> entitiesMeta);
      }

      Meta categoriesMeta = getMeta();
      if (categoriesMeta == null) {
        when(mockedDAO.getCategoriesMeta()).thenThrow(new Exception("Non-mocked method categoriesMeta invoked"));
      } else {
        when(mockedDAO.getCategoriesMeta()).thenAnswer(invocationOnMock -> categoriesMeta);
      }

    } catch (Exception e) {

    }

    return mockedDAO;
  }

  protected Map<String, Entity> getEntityMetadataByKbId() {
    return null;
  }

  protected Map<String, Category> getCategoryMetadataByKbId() {
    return null;
  }

  protected Meta getMeta() {
    return null;
  }


}




