package pl.itcrowd.base.test;

import pl.itcrowd.base.test.mocks.ConversationMock;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import pl.itcrowd.utils.test.LibraryResolver;

import java.io.File;

public class Packager {
// ------------------------------ FIELDS ------------------------------

    private WebArchive archive;

// --------------------------- CONSTRUCTORS ---------------------------

    public Packager(Class testClass)
    {
        archive = ShrinkWrap.create(WebArchive.class, testClass.getSimpleName() + ".war");
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public WebArchive getArchive()
    {
        return archive;
    }

// -------------------------- OTHER METHODS --------------------------

    public Packager addAllClasses()
    {
        archive.addPackages(true, "pl.itcrowd.base");
        archive.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        archive.addAsResource("META-INF/seam-beans.xml", "META-INF/seam-beans.xml");
        archive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-deployment-structure.xml"));
        return this;
    }

    public Packager addAllLibraries()
    {
        archive.addAsLibraries(LibraryResolver.resolve("commons-logging"));
        archive.addAsLibraries(LibraryResolver.resolve("commons-pool"));
        archive.addAsLibraries(LibraryResolver.resolve("freemarker"));
        archive.addAsLibraries(LibraryResolver.resolve("itcrowd-utils"));
        archive.addAsLibraries(LibraryResolver.resolve("joda-time"));
        archive.addAsLibraries(LibraryResolver.resolve("junit"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-api"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-common"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-core"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-spi"));
        archive.addAsLibraries(LibraryResolver.resolve("poi"));
        archive.addAsLibraries(LibraryResolver.resolve("prettyfaces-jsf2"));
        archive.addAsLibraries(LibraryResolver.resolve("richfaces-components-api"));
        archive.addAsLibraries(LibraryResolver.resolve("richfaces-components-ui"));
        archive.addAsLibraries(LibraryResolver.resolve("richfaces-core-api"));
        archive.addAsLibraries(LibraryResolver.resolve("richfaces-core-impl"));
        archive.addAsLibraries(LibraryResolver.resolve("seam3-persistence-framework"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-conversation-spi"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-conversation-weld"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-faces"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-faces-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-international"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-international-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-mail"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-mail-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-persistence"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-persistence-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-security"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-security-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-transaction"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-transaction-api"));
        archive.addAsLibraries(LibraryResolver.resolve("solder-api"));
        archive.addAsLibraries(LibraryResolver.resolve("solder-impl"));
        archive.addAsLibraries(LibraryResolver.resolve("solder-logging"));
        return this;
    }

    public Packager addAllTemplateResources()
    {
        archive.addAsWebResource(new File("src/main/webapp/layout/template.xhtml"), ArchivePaths.create("layout/template.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/layout/adminTemplate.xhtml"), ArchivePaths.create("layout/adminTemplate.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/resources/components/edit.xhtml"), ArchivePaths.create("resources/components/edit.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/resources/components/formChangeEvent.xhtml"),
            ArchivePaths.create("resources/components/formChangeEvent.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/resources/components/formChangeListener.xhtml"),
            ArchivePaths.create("resources/components/formChangeListener.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/resources/components/rowSelector.xhtml"),
            ArchivePaths.create("resources/components/rowSelector.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/resources/org/richfaces/formChangeListener.js"),
            ArchivePaths.create("resources/org/richfaces/formChangeListener.js"));

        archive.addAsWebResource(new File("src/main/webapp/js/chosen.jquery.js"), ArchivePaths.create("js/chosen.jquery.js"));
        archive.addAsWebResource(new File("src/main/webapp/stylesheet/theme_admin.css"), ArchivePaths.create("stylesheet/theme_admin.css"));
        archive.addAsWebResource(new File("src/main/webapp/stylesheet/chosen.css"), ArchivePaths.create("stylesheet/chosen.css"));
        archive.addAsWebResource(new File("src/main/webapp/stylesheet/jquery-ui.custom.css"), ArchivePaths.create("stylesheet/jquery-ui.custom.css"));
        return this;
    }

    public Packager addAllViews()
    {
        archive.addAsWebResource(new File("src/main/webapp/error.html"), ArchivePaths.create("error.html"));
        archive.addAsWebResource(new File("src/main/webapp/view/401.xhtml"), ArchivePaths.create("view/401.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/view/error.xhtml"), ArchivePaths.create("view/error.xhtml"));
        archive.addAsWebResource(new File("src/main/webapp/view/home.xhtml"), ArchivePaths.create("view/home.xhtml"));
        return this;
    }

    public Packager addCITLibraries()
    {
        archive.addAsLibraries(LibraryResolver.resolve("freemarker"));
        archive.addAsLibraries(LibraryResolver.resolve("itcrowd-utils"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-api"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-common"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-core"));
        archive.addAsLibraries(LibraryResolver.resolve("picketlink-idm-spi"));
        archive.addAsLibraries(LibraryResolver.resolve("poi"));
        archive.addAsLibraries(LibraryResolver.resolve("richfaces-components-api"));
        archive.addAsLibraries(LibraryResolver.resolve("richfaces-core-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam3-mailman"));
        archive.addAsLibraries(LibraryResolver.resolve("seam3-persistence-framework"));
        archive.addAsLibraries(LibraryResolver.resolve("seam3-persistence-framework-domain"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-faces"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-faces-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-international"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-international-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-jms"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-jms-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-mail"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-mail-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-persistence"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-persistence-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-security"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-security-api"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-transaction"));
        archive.addAsLibraries(LibraryResolver.resolve("seam-transaction-api"));
        archive.addAsLibraries(LibraryResolver.resolve("solder-api"));
        archive.addAsLibraries(LibraryResolver.resolve("solder-impl"));
        archive.addAsLibraries(LibraryResolver.resolve("solder-logging"));
        archive.addAsLibraries(LibraryResolver.resolve("mockito-all"));
        return this;
    }

    public Packager addClass(Class<?> clazz)
    {
        archive.addClass(clazz);
        return this;
    }

    public Packager addPropertyFiles()
    {
        archive.addAsResource("view.properties", "view.properties");
//        archive.addAsResource("view_de.properties", "view_de.properties");
//        archive.addAsResource("view_pl.properties", "view_pl.properties");
        archive.addAsResource("domain.properties", "domain.properties");
//        archive.addAsResource("domain_de.properties", "domain_de.properties");
        archive.addAsResource("business.properties", "business.properties");
//        archive.addAsResource("business_pl.properties", "business_pl.properties");
        archive.addAsResource("validation.properties", "validation.properties");

        return this;
    }

    public WebArchive buildCITPackage()
    {
        final BeansDescriptor beansDescriptor = getBeansDescriptor();
        beansDescriptor.getOrCreateAlternatives().clazz(ConversationMock.class.getCanonicalName()).clazz(ProjectTestConfig.class.getCanonicalName());
        addClass(ConversationMock.class);
        this.addCITLibraries()
            .addAllClasses()
            .addJRebelIfExists()
            .getArchive()
            .addAsWebInfResource(new StringAsset(beansDescriptor.exportAsString()), "beans.xml");
        archive.delete("WEB-INF/classes/pl/itcrowd/base/web/SessionInvalidator.class");
        archive.addClass(TestServletContextListener.class);
        return archive;
    }

    public WebArchive buildPackage()
    {
        return this.addAllLibraries()
            .addAllClasses()
            .addAllTemplateResources()
            .addPropertyFiles()
            .addAllViews()
            .addRichFacesRelatedAssets()
            .addJRebelIfExists()
            .getArchive()
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml")
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/pretty-config.xml"), "pretty-config.xml")
            .setWebXML(new File("target/base/WEB-INF/web.xml"))
            .addAsWebInfResource(new StringAsset(getBeansDescriptor().exportAsString()), "beans.xml");
    }

    private Packager addJRebelIfExists()
    {
        final File jrebelFile = new File("target/classes/rebel.xml");
        if (jrebelFile.exists()) {
            archive.addAsResource(jrebelFile, "rebel.xml");
        } else {
            System.err.println(jrebelFile.getAbsolutePath() + " does not exist and won't be packaged");
        }
        return this;
    }

    private Packager addRichFacesRelatedAssets()
    {
//        archive.addAsResource("META-INF/custom-mapping.properties", "META-INF/custom-mapping.properties");
//        archive.addAsWebResource(new File("src/main/webapp/resources/jquery-patched.js"), "resources/jquery-patched.js");
        return this;
    }

    private BeansDescriptor getBeansDescriptor()
    {
        return Descriptors.create(BeansDescriptor.class).getOrCreateInterceptors().up().getOrCreateAlternatives().up();
    }
}

