package pl.itcrowd.base.product.business;

import pl.itcrowd.base.domain.Product;
import pl.itcrowd.base.framework.business.EntityQuery;
import pl.itcrowd.seam3.persistence.conditions.DynamicParameter;
import pl.itcrowd.seam3.persistence.conditions.FreeCondition;

import java.io.Serializable;
import java.util.Arrays;

public class ProductList extends EntityQuery<Product> implements Serializable {

    private Criteria searchCriteria = new Criteria();

    public ProductList()
    {
        setEjbql("select pr from Psychic p join p.products pr");
        final FreeCondition categoryIdCondition = new FreeCondition("pr.category.id=", searchCriteria.categoryIdBridge);
        final FreeCondition psychicIdCondition = new FreeCondition("p.id=", searchCriteria.psychicIdBridge);
        final FreeCondition quantityCondition = new FreeCondition("pr.quantity>", searchCriteria.quantityBridge);
        final FreeCondition searchFilterCondition = new FreeCondition("lower(pr.name) like lower(concat(", searchCriteria.searchFilterBridge, ",'%'))");
        final FreeCondition tagIdCondition = new FreeCondition(searchCriteria.tagIdBridge, " member pr.tags");
        setConditions(Arrays.asList(categoryIdCondition, psychicIdCondition, quantityCondition, searchFilterCondition, tagIdCondition));
        setOrderDirection(DIR_ASC);
        setOrderColumn("pr.name");
    }

    public Criteria getSearchCriteria()
    {
        return searchCriteria;
    }

    public void sortByNameAsc()
    {
        setOrderDirection(DIR_ASC);
        setOrderColumn("pr.name");
    }

    public void sortByNameDesc()
    {
        setOrderDirection(DIR_DESC);
        setOrderColumn("pr.name");
    }

    public void sortByPriceAsc()
    {
        setOrderDirection(DIR_ASC);
        setOrderColumn("pr.price");
    }

    public void sortByPriceDesc()
    {
        setOrderDirection(DIR_DESC);
        setOrderColumn("pr.price");
    }

    public static class Criteria implements Serializable {

        private Long categoryId;

        private DynamicParameter<Long> categoryIdBridge = new DynamicParameter<Long>() {
            @Override
            public Long getValue()
            {
                return categoryId;
            }
        };

        private Long psychicId;

        private DynamicParameter<Long> psychicIdBridge = new DynamicParameter<Long>() {
            @Override
            public Long getValue()
            {
                return psychicId;
            }
        };

        private DynamicParameter<Long> quantityBridge = new DynamicParameter<Long>() {
            @Override
            public Long getValue()
            {
                return quantity;
            }
        };

        private DynamicParameter<String> searchFilterBridge = new DynamicParameter<String>() {
            @Override
            public String getValue()
            {
                return searchFilter;
            }
        };

        private DynamicParameter<Long> tagIdBridge = new DynamicParameter<Long>() {
            @Override
            public Long getValue()
            {
                return tagId;
            }
        };

        private String searchFilter;

        private Long quantity;

        private Long tagId;

        public void setTagId(Long tagId)
        {
            this.tagId = tagId;
        }

        public Long getCategoryId()
        {
            return categoryId;
        }

        public void setCategoryId(Long id)
        {
            this.categoryId = id;
            psychicId = null;
        }

        public void setPsychicId(Long psychicId)
        {
            this.psychicId = psychicId;
            categoryId = null;
        }

        public void setQuantity(Long quantity)
        {
            this.quantity = quantity;
        }

        public void setSearchFilter(String searchFilter)
        {
            this.searchFilter = searchFilter;
        }
    }
}

