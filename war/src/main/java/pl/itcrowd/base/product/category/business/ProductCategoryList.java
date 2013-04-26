package pl.itcrowd.base.product.category.business;

import pl.itcrowd.base.domain.ProductCategory;
import pl.itcrowd.base.framework.business.EntityQuery;

import java.io.Serializable;

public class ProductCategoryList extends EntityQuery<ProductCategory> implements Serializable {

    public ProductCategoryList()
    {
        setEjbql("select pc from ProductCategory pc where pc.parentCategory is null");
    }
}
