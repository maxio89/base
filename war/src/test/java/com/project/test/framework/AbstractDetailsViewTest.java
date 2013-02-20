package com.project.test.framework;

import com.project.framework.business.EntityHome;
import com.project.framework.view.AbstractDetailsView;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import pl.com.it_crowd.seam.framework.Identifiable;

import javax.enterprise.context.RequestScoped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractDetailsViewTest {
// ------------------------------ FIELDS ------------------------------

    private DetailsView detailsView;

    private EntityHome entityHome;

// -------------------------- OTHER METHODS --------------------------

    @Test
    public void cancel() throws Exception
    {
//        Given
        assertFalse(detailsView.isModified());

//        When
        final String outcome = detailsView.cancel();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome);
        assertFalse(detailsView.isSaveChangesConfirmationVisible());
    }

    @Test
    public void cancelWhenModifiedSaveAndProceed() throws Exception
    {
//        Given
        when(entityHome.persist()).thenReturn(true);
        detailsView.formChanged();
        assertTrue(detailsView.isModified());

//        When
        final String outcome = detailsView.cancel();
        assertEquals(DetailsView.OUTCOME_FAILURE, outcome);
        assertTrue(detailsView.isSaveChangesConfirmationVisible());
        verify(entityHome, never()).clearInstance();
        final String outcome2 = detailsView.saveAndProceed();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome2);
        assertFalse(detailsView.isSaveChangesConfirmationVisible());
        verify(entityHome, times(1)).clearInstance();
    }

    @Test
    public void createNew() throws Exception
    {
//        Given
        when(entityHome.getInstance()).thenReturn(new Identifiable<Long>() {
            @Override
            public Long getId()
            {
                return null;
            }
        });
        assertFalse(detailsView.isModified());


//        When
        final String outcome = detailsView.createNew();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome);
        assertFalse(detailsView.isModified());
        verify(entityHome, times(1)).clearInstance();
    }

    @Test
    public void createNewWhenModified() throws Exception
    {
//        Given
        when(entityHome.getInstance()).thenReturn(new Identifiable<Long>() {
            @Override
            public Long getId()
            {
                return null;
            }
        });
        detailsView.formChanged();
        assertTrue(detailsView.isModified());


//        When
        final String outcome = detailsView.createNew();
        assertEquals(DetailsView.OUTCOME_FAILURE, outcome);
        assertTrue(detailsView.isSaveChangesConfirmationVisible());
        assertTrue(detailsView.isModified());
        verify(entityHome, never()).clearInstance();
        final String outcome2 = detailsView.proceedWithoutSave();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome2);
        assertFalse(detailsView.isModified());
        verify(entityHome, times(1)).clearInstance();
        verify(entityHome, never()).persist();
        verify(entityHome, never()).update();
    }

    @Test
    public void defaultConstructor()
    {
//        Given

//        When
        new DetailsView();
    }

    @Test
    public void failSave() throws Exception
    {
//        Given
        when(entityHome.update()).thenReturn(false);
        when(entityHome.isIdDefined()).thenReturn(true);
        when(entityHome.getInstance()).thenReturn(new Identifiable<Long>() {
            @Override
            public Long getId()
            {
                return 1L;
            }
        });
        detailsView.formChanged();
        assertTrue(detailsView.isModified());

//        When
        final String outcome = detailsView.save();

//        Then
        assertEquals(DetailsView.OUTCOME_FAILURE, outcome);
        assertTrue(detailsView.isModified());
        verify(entityHome, never()).persist();
        verify(entityHome, times(1)).update();
    }

    @Test
    public void failSaveAndProceed() throws Exception
    {
//        Given

//        When
        final String outcome = detailsView.saveAndProceed();

//        Then
        assertEquals(DetailsView.OUTCOME_FAILURE, outcome);
    }

    @Test
    public void proceedWithoutSaveWithoutProceedAction() throws Exception
    {
//        Given

//        When
        final String outcome = detailsView.proceedWithoutSave();

//        Then
        assertEquals(DetailsView.OUTCOME_FAILURE, outcome);
    }

    @Test
    public void saveAndProceedWithoutProceedAction() throws Exception
    {
//        Given
        when(entityHome.persist()).thenReturn(true);

//        When
        final String outcome = detailsView.saveAndProceed();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome);
    }

    @Test
    public void saveNonTransient() throws Exception
    {
//        Given
        when(entityHome.update()).thenReturn(true);
        when(entityHome.isIdDefined()).thenReturn(true);
        when(entityHome.getInstance()).thenReturn(new Identifiable<Long>() {
            @Override
            public Long getId()
            {
                return 1L;
            }
        });
        detailsView.formChanged();
        assertTrue(detailsView.isModified());

//        When
        final String outcome = detailsView.save();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome);
        assertFalse(detailsView.isModified());
        verify(entityHome, never()).persist();
        verify(entityHome, times(1)).update();
    }

    @Test
    public void saveTransient() throws Exception
    {
//        Given
        when(entityHome.persist()).thenReturn(true);
        assertFalse(detailsView.isModified());

//        When
        final String outcome = detailsView.save();

//        Then
        assertEquals(DetailsView.OUTCOME_SUCCESS, outcome);
        assertFalse(detailsView.isModified());
        verify(entityHome, times(1)).persist();
        verify(entityHome, never()).update();
    }

    @Test
    public void select() throws Exception
    {
//        Given
        final Identifiable<Long> identifiable = new Identifiable<Long>() {
            @Override
            public Long getId()
            {
                return 3L;
            }
        };
        assertFalse(detailsView.isModified());

//        When
        //noinspection unchecked
        detailsView.select(identifiable);

//        Then
        assertFalse(detailsView.isSaveChangesConfirmationVisible());
        verify(entityHome, times(1)).setId(3L);
    }

    @Test
    public void selectWhenModifiedAndProceedWithoutSave() throws Exception
    {
//        Given
        final Identifiable<Long> identifiable = new Identifiable<Long>() {
            @Override
            public Long getId()
            {
                return 3L;
            }
        };
        when(entityHome.persist()).thenReturn(true);
        detailsView.formChanged();
        assertTrue(detailsView.isModified());

//        When
        //noinspection unchecked
        detailsView.select(identifiable);
        assertTrue(detailsView.isSaveChangesConfirmationVisible());
        verify(entityHome, never()).setId(any());
        final String outcome = detailsView.saveAndProceed();

//        Then
        assertNull(outcome);
        assertFalse(detailsView.isSaveChangesConfirmationVisible());
        verify(entityHome, times(1)).setId(3L);
        verify(entityHome, times(1)).persist();
        verify(entityHome, never()).update();
    }

    @Before
    public void setUp() throws Exception
    {
//        Given
        entityHome = mock(EntityHome.class);
        detailsView = new DetailsView(mock(Logger.class), mock(Messages.class), entityHome);
    }

// -------------------------- INNER CLASSES --------------------------

    @RequestScoped
    public static class DetailsView extends AbstractDetailsView {
// ------------------------------ FIELDS ------------------------------

        private static final String OUTCOME_SUCCESS = "success";

        private EntityHome entityHome;

// --------------------------- CONSTRUCTORS ---------------------------

        public DetailsView()
        {
        }

        public DetailsView(Logger logger, Messages messages, EntityHome entityHome)
        {
            super(logger, messages);
            this.entityHome = entityHome;
        }

// -------------------------- OTHER METHODS --------------------------

        @Override
        public EntityHome getHome()
        {
            return entityHome;
        }

        @Override
        public String getOutcomeSuccess()
        {
            return OUTCOME_SUCCESS;
        }
    }
}
