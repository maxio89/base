package pl.itcrowd.base.test.framework.view;

import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.framework.view.ListViewHelper;
import junit.framework.Assert;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class ListViewHelperTest {
// ------------------------------ FIELDS ------------------------------

    private EntityHome<Object> entityHomeMock;

    private Messages messagesMock;

// -------------------------- OTHER METHODS --------------------------

    @Before
    public void before()
    {
        //noinspection unchecked
        entityHomeMock = mock(EntityHome.class);
        messagesMock = mock(Messages.class);
    }

    @Test
    public void noSelection()
    {
//        Given
        HashMap<Object, Boolean> selection = new HashMap<Object, Boolean>();
        selection.put(new Object(), false);
        selection.put(new Object(), false);

//        When
        String result = ListViewHelper.removeSelectedElements(selection, entityHomeMock, messagesMock);

//        Then
        Assert.assertEquals(ListViewHelper.OUTCOME_NO_ELEMENT_SELECTED, result);
        Mockito.verify(entityHomeMock, times(0)).remove(anyCollectionOf(Object.class));
        Mockito.verify(messagesMock, times(0)).info(any(BundleKey.class));
        Mockito.verify(messagesMock, times(1)).warn(any(BundleKey.class));
    }

    @Test
    public void removeEmptyCollection()
    {
//        Given
        HashMap<Object, Boolean> selection = new HashMap<Object, Boolean>();

//        When
        String result = ListViewHelper.removeSelectedElements(selection, entityHomeMock, messagesMock);

//        Then
        Assert.assertEquals(ListViewHelper.OUTCOME_NO_ELEMENT_SELECTED, result);
        Mockito.verify(entityHomeMock, times(0)).remove(anyCollectionOf(Object.class));
        Mockito.verify(messagesMock, times(0)).info(any(BundleKey.class));
        Mockito.verify(messagesMock, times(1)).warn(any(BundleKey.class));
    }

    @Test
    public void removeTwoItems()
    {
//        Given
        HashMap<Object, Boolean> selection = new HashMap<Object, Boolean>();
        Object entityA = new Object();
        Object entityB = new Object();
        Object entityC = new Object();
        selection.put(entityA, false);
        selection.put(entityB, true);
        selection.put(entityC, true);

//        When
        String result = ListViewHelper.removeSelectedElements(selection, entityHomeMock, messagesMock);

//        Then
        Assert.assertEquals(ListViewHelper.OUTCOME_SUCCESS, result);
        Mockito.verify(entityHomeMock, times(1)).remove(anyCollectionOf(Object.class));
        Mockito.verify(entityHomeMock, times(1)).remove(new HashSet<Object>(Arrays.asList(entityB, entityC)));
        Mockito.verify(messagesMock, times(1)).info(any(BundleKey.class));
        Mockito.verify(messagesMock, times(0)).warn(any(BundleKey.class));
    }
}
