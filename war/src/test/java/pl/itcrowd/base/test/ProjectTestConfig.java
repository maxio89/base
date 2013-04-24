package pl.itcrowd.base.test;

import org.jboss.solder.servlet.WebApplication;
import pl.itcrowd.base.setting.business.ProjectConfig;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;

@Alternative
@Specializes
public class ProjectTestConfig extends ProjectConfig {
// -------------------------- OTHER METHODS --------------------------

  @PostConstruct
  public void init()
  {
    super.init();
    reload();
  }

  @Override
  protected void onStartup(WebApplication ignore)
  {
    /**
     * We don't want to init this component on startup because database may not be ready during deployment.
     */
  }
}
