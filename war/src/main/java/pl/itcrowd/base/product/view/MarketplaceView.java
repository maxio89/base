package pl.itcrowd.base.product.view;

import pl.itcrowd.base.domain.Product;
import pl.itcrowd.base.domain.ProductCategory;
import pl.itcrowd.base.domain.Tag;
import pl.itcrowd.base.order.cart.business.DiscountProcessor;
import pl.itcrowd.base.product.business.ProductList;
import pl.itcrowd.base.product.category.business.ProductCategoryList;
import pl.itcrowd.base.product.tag.business.TagHome;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@ViewScoped
public class MarketplaceView implements Serializable {

    private static final int ROWS_NUMBER = 8;

    private static final int TOP_TEN = 10;

    private Long categoryId;

    private ProductList productList;

    private Long psychicId;

    private SortBy sortBy;

    private DiscountProcessor discountProcessor;

    private ProductCategoryList productCategoryList;

    private List<ProductCategory> productParentCategories;

    private Long tagId;

    private TagHome tagHome;

    private List<Tag> tags;

    @SuppressWarnings("UnusedDeclaration")
    public MarketplaceView()
    {
    }

    @SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
    @Inject
    public MarketplaceView(ProductCategoryList productCategoryList, ProductList productList, DiscountProcessor discountProcessor, TagHome tagHome)
    {
        this.productList = productList;
        this.discountProcessor = discountProcessor;
        this.productCategoryList = productCategoryList;
        this.tagHome = tagHome;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
        productList.getSearchCriteria().setCategoryId(categoryId);
        psychicId = null;
    }

    public List<ProductCategory> getProductParentCategories()
    {
        if (productParentCategories == null) {
            productParentCategories = productCategoryList.getResultList();
        }
        return productParentCategories;
    }

    public ProductList getProductList()
    {
        return productList;
    }

    public Long getPsychicId()
    {
        return psychicId;
    }

    public void setPsychicId(Long psychicId)
    {
        this.psychicId = psychicId;
        productList.getSearchCriteria().setPsychicId(psychicId);
        categoryId = null;
    }

    public SortBy getSortBy()
    {
        return sortBy;
    }

    public void setSortBy(SortBy sortBy)
    {
        this.sortBy = sortBy;
        if (SortBy.NAME_ASC.equals(sortBy)) {
            productList.sortByNameAsc();
            productList.refresh();
        } else if (SortBy.NAME_DESC.equals(sortBy)) {
            productList.sortByNameDesc();
            productList.refresh();
        } else if (SortBy.PRICE_ASC.equals(sortBy)) {
            productList.sortByPriceAsc();
            productList.refresh();
        } else if (SortBy.PRICE_DESC.equals(sortBy)) {
            productList.sortByPriceDesc();
            productList.refresh();
        }
    }

    public Long getTagId()
    {
        return tagId;
    }

    public void setTagId(Long tagId)
    {
        this.tagId = tagId;
        productList.getSearchCriteria().setTagId(tagId);
    }

    public void initView()
    {
        productList.setMaxResults(ROWS_NUMBER);
        this.productList.getSearchCriteria().setQuantity(0L);
    }

    public BigDecimal getBestPriceForProduct(Product product)
    {
        return discountProcessor.getBestPriceAfterSpecialPriceDiscounts(product);
    }

    public List<Tag> getTags()
    {
        if (tags == null) {
            tags = tagHome.getTopTags(TOP_TEN);
        }
        return tags;
    }

    public enum SortBy {
        NAME_ASC,
        NAME_DESC,
        PRICE_ASC,
        PRICE_DESC
    }
}
