package pl.itcrowd.base.product.category.view;

import org.jboss.seam.international.status.Messages;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class CategoryListView implements Serializable {

    private ProductCategoryHome categoryHome;

    private Messages messages;

    private Map<ProductCategory, List<ProductCategory>> productCategories = new HashMap<ProductCategory, List<ProductCategory>>();

    private ProductHome productHome;

    private ProductCategory selectedCategory;

    private ProductCategory selectedChildCategory;

    @SuppressWarnings("UnusedDeclaration")
    public CategoryListView()
    {
    }

    @SuppressWarnings("UnusedDeclaration")
    @Inject
    public CategoryListView(ProductCategoryHome categoryHome, ProductHome productHome, Messages messages)
    {
        this.categoryHome = categoryHome;
        this.productHome = productHome;
        this.messages = messages;
    }

    public ProductCategory getEditedCategory()
    {
        return categoryHome.getInstance();
    }

    public List<ProductCategory> getProductChildCategories()
    {
        if (selectedCategory != null) {
            final List<ProductCategory> categories = productCategories.get(selectedCategory);
            if (categories != null) {
                return categories;
            }
        }

        return Collections.emptyList();
    }

    public List<ProductCategory> getProductParentCategories()
    {
        return productCategories.get(null);
    }

    public ProductCategory getSelectedCategory()
    {
        return selectedCategory;
    }

    public void setSelectedCategory(ProductCategory selectedCategory)
    {
        this.selectedCategory = selectedCategory;
        categoryHome.clearInstance();
        if (selectedCategory == null || selectedCategory.getId() == null) {
            categoryHome.setInstance(selectedCategory);
        } else {
            categoryHome.setId(selectedCategory.getId());
        }
    }

    public ProductCategory getSelectedChildCategory()
    {
        return selectedChildCategory;
    }

    public void setSelectedChildCategory(ProductCategory selectedChildCategory)
    {
        categoryHome.clearInstance();
        if (selectedChildCategory == null || selectedChildCategory.getId() == null) {
            categoryHome.setInstance(selectedChildCategory);
        } else {
            categoryHome.setId(selectedChildCategory.getId());
        }
        this.selectedChildCategory = selectedChildCategory;
    }

    public String delete()
    {
        categoryHome.clearInstance();
        categoryHome.setId(selectedCategory.getId());
        categoryHome.remove();
        initView();
        newCategory();
        messages.info(BundleKeys.CATEGORY_DELETE);
        return Constants.OUTCOME_SUCCESS;
    }

    public String deleteChild()
    {
        categoryHome.clearInstance();
        categoryHome.setId(selectedChildCategory.getId());
        categoryHome.remove();
        initView();
        newChildCategory();
        messages.info(BundleKeys.CATEGORY_DELETE);
        return Constants.OUTCOME_SUCCESS;
    }

    public void initView()
    {
        productCategories.clear();
        for (ProductCategory productCategory : categoryHome.getAllCategories()) {
            final ProductCategory parentCategory = productCategory.getParentCategory();
            List<ProductCategory> cache = productCategories.get(parentCategory);
            if (cache == null) {
                cache = new ArrayList<ProductCategory>();
                productCategories.put(parentCategory, cache);
            }
            cache.add(productCategory);
        }
    }

    public boolean isProductsForCategories()
    {
        for (ProductCategory category1 : getProductChildCategories()) {
            if (category1.getParentCategory().equals(selectedCategory) && productHome.getProductsForCategory(category1) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductsForChildCategory()
    {
        return selectedChildCategory != null && selectedChildCategory.getId() != null && productHome.getProductsForCategory(selectedChildCategory) != 0;
    }

    public boolean isUnselectedChild()
    {
        return categoryHome.getInstance().getParentCategory() == null || categoryHome.getInstance().getParentCategory().getId() == null;
    }

    public String newCategory()
    {
        setSelectedChildCategory(null);
        setSelectedCategory(new ProductCategory());
        return Constants.OUTCOME_SUCCESS;
    }

    public String newChildCategory()
    {
        if (selectedCategory == null) {
            messages.error(BundleKeys.NO_CATEGORY_SELECTED);
            return Constants.OUTCOME_FAILURE;
        }
        final ProductCategory childCategory = new ProductCategory();
        childCategory.setParentCategory(selectedCategory);
        setSelectedChildCategory(childCategory);
        return Constants.OUTCOME_SUCCESS;
    }

    public void save()
    {
        if (!categoryHome.checkDuplicate().isEmpty()) {
            messages.error(BundleKeys.DUPLICATE_CATEGORY);
            return;
        }
        final boolean result = categoryHome.isIdDefined() ? categoryHome.update() : categoryHome.persist();
        initView();

        if (result) {
            messages.info(BundleKeys.CATEGORY_UPDATE);
        } else {
            messages.info(BundleKeys.CATEGORY_ADD);
        }
    }
}
