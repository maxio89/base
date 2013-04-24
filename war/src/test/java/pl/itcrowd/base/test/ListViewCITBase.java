package pl.itcrowd.base.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public abstract class ListViewCITBase<T> {
// -------------------------- OTHER METHODS --------------------------

//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void filterSelection()
//  {
////            Given
//    final T entity = newInstane();
//    getListView().getEntitySelection().put(entity, false);
//    getListView().getEntitySelection().put(getListView().getEntityList().getResultList().get(0), false);
//    Assert.assertEquals(2, getListView().getEntitySelection().size());
//    assertFalse(getListView().getEntityList().getResultList().contains(entity));
//
////            When
//    getListView().filterSelection();
//
////            Then
//    Assert.assertEquals(1, getListView().getEntitySelection().size());
//  }
//
//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void getEntityList()
//  {
////            Given
//
////            When
//    final EntityQuery<T> entityList = getListView().getEntityList();
//
////            Then
//    assertNotNull(entityList);
//  }
//
//  public abstract AbstractListView<T> getListView();
//
//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void init()
//  {
////            Given
//
////            When
//
////            Then
//    Assert.assertEquals((Integer) Constants.DEFAULT_MAX_RESULTS, getListView().getEntityList().getMaxResults());
//  }
//
//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void onPeriodPersisted()
//  {
//    this.onEntityEvent(new AnnotationLiteral<EntityPersisted>() {
//    });
//  }
//
//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void onPeriodRemoved()
//  {
//    this.onEntityEvent(new AnnotationLiteral<EntityRemoved>() {
//    });
//  }
//
//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void onPeriodUpdated()
//  {
//    this.onEntityEvent(new AnnotationLiteral<EntityUpdated>() {
//    });
//  }
//
//  @ConversationScopeRequired
//  @ViewScopeRequired
//  @Test
//  public void removeSelectedEntities()
//  {
////            Given
//    final T entity1 = getListView().getEntityList().getResultList().get(0);
//    final T entity2 = getListView().getEntityList().getResultList().get(1);
//    getListView().getEntitySelection().put(entity1, true);
//    getListView().getEntitySelection().put(entity2, false);
//    Assert.assertEquals(2, getListView().getEntitySelection().size());
//
////            When
//    final String outcome = getListView().removeSelectedEntities();
//
////            Then
//    assertEquals(ListViewHelper.OUTCOME_SUCCESS, outcome);
//    assertTrue(getListView().getEntitySelection().isEmpty());
//    assertFalse(getListView().getEntityList().getResultList().contains(entity1));
//    assertTrue(getListView().getEntityList().getResultList().contains(entity2));
//  }
//
//  protected abstract Event<T> getEvent();
//
//  protected abstract T newInstane();
//
//  private void onEntityEvent(AnnotationLiteral eventAnnotationLiteral)
//  {
////            Given
//    final T fakeEntity = newInstane();
//    getListView().getEntityList().getResultList().add(fakeEntity);
//    assertTrue(getListView().getEntityList().getResultList().contains(fakeEntity));
//
////            When
//    final T newInstance = newInstane();
//    //noinspection unchecked
//    getEvent().select((Class<T>) newInstance.getClass(), eventAnnotationLiteral).fire(newInstance);
//
////            Then
//    assertFalse(getListView().getEntityList().getResultList().contains(fakeEntity));
//  }
}
