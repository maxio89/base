package pl.itcrowd.base.test.user;

import pl.itcrowd.base.test.Packager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import pl.com.it_crowd.arquillian.mock_contexts.ConversationScopeRequired;
import pl.com.it_crowd.arquillian.mock_contexts.FacesContextRequired;
import pl.com.it_crowd.arquillian.mock_contexts.MockFacesContextProducer;

import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Transactional
@RunWith(Arquillian.class)
public class UserLoginCIT {
// ------------------------------ FIELDS ------------------------------

    @Inject
    private Credentials credentials;

    @Inject
    private Identity identity;

// -------------------------- STATIC METHODS --------------------------

    @Deployment
    public static WebArchive createDeployment()
    {
        return new Packager(UserLoginCIT.class).buildCITPackage();
    }

// -------------------------- OTHER METHODS --------------------------

    @MockFacesContextProducer
    public FacesContext mockFacesContext()
    {
        final Application application = Mockito.mock(Application.class);
        Mockito.when(application.getProjectStage()).thenReturn(ProjectStage.Development);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        Mockito.when(facesContext.getApplication()).thenReturn(application);
        return facesContext;
    }

    @FacesContextRequired
    @ConversationScopeRequired
    @Test
    public void userLogIn()
    {
        identity.login();
        Assert.assertEquals(true, identity.isLoggedIn());
    }
}

