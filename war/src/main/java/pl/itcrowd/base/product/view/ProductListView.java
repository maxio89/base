package pl.itcrowd.base.product.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.domain.Product;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.framework.business.EntitySelected;
import pl.itcrowd.base.framework.view.AbstractListView;
import pl.itcrowd.base.product.business.ProductHome;
import pl.itcrowd.base.product.business.ProductList;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.web.BundleKeys;

import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ProductListView extends AbstractListView<Product> implements Serializable {

    private ProductHome productHome;

    private ProductList productList;

    @SuppressWarnings("UnusedDeclaration")
    public ProductListView()
    {
    }

    @SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
    @Inject
    public ProductListView(@CurrentUser User currentUser, ProductHome productHome, ProductList productList, @EntitySelected Event<Product> entitySelectedEvent, Messages messages)
    {
        super(productHome, productList, entitySelectedEvent, messages);
        this.productHome = productHome;
        this.productList = productList;
        this.productList.getSearchCriteria().setPsychicId(currentUser.getId());
    }

    public ProductList getProductList()
    {
        return productList;
    }

    public void remove(Product product)
    {
        if (product != null) {
            productHome.setId(product.getId());
            productHome.remove();
            messages.info(BundleKeys.PRODUCT_REMOVED);
        }
    }
}
