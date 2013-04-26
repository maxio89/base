package pl.itcrowd.base.product.view;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;
import org.joda.time.LocalDate;
import org.richfaces.event.FileUploadEvent;
import pl.itcrowd.base.Constants;
import pl.itcrowd.base.domain.Discount;
import pl.itcrowd.base.domain.Photo;
import pl.itcrowd.base.domain.Product;
import pl.itcrowd.base.domain.ProductCategory;
import pl.itcrowd.base.domain.ProductFile;
import pl.itcrowd.base.domain.QuantityDiscount;
import pl.itcrowd.base.domain.Shop;
import pl.itcrowd.base.domain.SpecialPriceDiscount;
import pl.itcrowd.base.domain.Tag;
import pl.itcrowd.base.domain.User;
import pl.itcrowd.base.order.cart.business.DiscountProcessor;
import pl.itcrowd.base.product.business.ProductHome;
import pl.itcrowd.base.product.category.business.ProductCategoryList;
import pl.itcrowd.base.product.tag.business.TagHome;
import pl.itcrowd.base.user.CurrentUser;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.util.ImageScaling;
import pl.itcrowd.base.web.BundleKeys;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Named
@ConversationScoped
public class ProductDetailsView implements Serializable {

    private static final int MAX_DISCOUNT = 99;

    private static final int MAX_FILES_UPLOAD = 6;

    private static final int MAX_FILE_SIZE = 9900000;

    private static final int THUMBNAIL_SIZE = 150;

    private static final int PHOTO_SIZE = 800;

    private Conversation conversation;

    private User currentUser;

    private DiscountProcessor discountProcessor;

    private List<QuantityDiscount> discountQuantityList;

    private List<SpecialPriceDiscount> discountSpecialList;

    private String fileUploadMessage;

    private ImageScaling imageScaling;

    private Logger logger;

    private Messages messages;

    private ProductCategoryList productCategoryList;

    private ProductHome productHome;

    private List<ProductCategory> productParentCategories;

    private List<ProductFile> productPhotos;

    private String productTags = "";

    private QuantityDiscount quantityDiscount;

    private ProductCategory selectedParentCategory;

    private Product selectedProduct;

    private Long selectedProductId;

    private Shop selectedShop;

    private SpecialPriceDiscount specialPriceDiscount;

    private TagHome tagHome;

    private UserHome userHome;

    @SuppressWarnings("UnusedDeclaration")
    public ProductDetailsView()
    {
    }

    @SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
    @Inject
    public ProductDetailsView(UserHome userHome, TagHome tagHome, ProductHome productHome, ProductCategoryList productCategoryList, @CurrentUser User currentUser,
                              Conversation conversation, Messages messages, DiscountProcessor discountProcessor, Logger logger, ImageScaling imageScaling)
    {
        this.userHome = userHome;
        this.tagHome = tagHome;
        this.productHome = productHome;
        this.productCategoryList = productCategoryList;
        this.currentUser = currentUser;
        this.conversation = conversation;
        this.messages = messages;
        this.discountProcessor = discountProcessor;
        this.logger = logger;
        this.imageScaling = imageScaling;
    }

    public List<QuantityDiscount> getDiscountQuantityList()
    {
        if (discountQuantityList == null) {
            discountQuantityList = new ArrayList<QuantityDiscount>();
            if (selectedProductId != null && selectedProduct.getDiscounts().size() > 0) {
                for (Discount discount : selectedProduct.getDiscounts()) {
                    if (discount instanceof QuantityDiscount) {
                        discountQuantityList.add((QuantityDiscount) discount);
                    }
                }
            }
        }
        return discountQuantityList;
    }

    public void setDiscountQuantityList(List<QuantityDiscount> discountQuantityList)
    {
        this.discountQuantityList = discountQuantityList;
    }

    public List<SpecialPriceDiscount> getDiscountSpecialList()
    {
        if (discountSpecialList == null) {
            discountSpecialList = new ArrayList<SpecialPriceDiscount>();
            if (selectedProductId != null && selectedProduct.getDiscounts().size() > 0) {
                for (Discount discount : selectedProduct.getDiscounts()) {
                    if (discount instanceof SpecialPriceDiscount) {
                        discountSpecialList.add((SpecialPriceDiscount) discount);
                    }
                }
            }
        }
        return discountSpecialList;
    }

    public void setDiscountSpecialList(List<SpecialPriceDiscount> discountSpecialList)
    {
        this.discountSpecialList = discountSpecialList;
    }

    public String getFileUploadMessage()
    {
        return fileUploadMessage;
    }

    public List<ProductCategory> getProductParentCategories()
    {
        if (productParentCategories == null) {
            productParentCategories = productCategoryList.getResultList();
        }
        return productParentCategories;
    }

    public List<ProductFile> getProductPhotos()
    {
        return productPhotos;
    }

    public void setProductPhotos(List<ProductFile> productPhotos)
    {
        this.productPhotos = productPhotos;
    }

    public String getProductTags()
    {
        return productTags;
    }

    public void setProductTags(String productTags)
    {
        this.productTags = productTags;
    }

    public QuantityDiscount getQuantityDiscount()
    {
        if (quantityDiscount == null) {
            quantityDiscount = new QuantityDiscount();
        }
        return quantityDiscount;
    }

    public ProductCategory getSelectedParentCategory()
    {
        return selectedParentCategory;
    }

    public void setSelectedParentCategory(ProductCategory selectedParentCategory)
    {
        this.selectedParentCategory = selectedParentCategory;
    }

    public Product getSelectedProduct()
    {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct)
    {
        this.selectedProduct = selectedProduct;
    }

    public Long getSelectedProductId()
    {
        return selectedProductId;
    }

    public void setSelectedProductId(Long selectedProductId)
    {
        productHome.setId(selectedProductId);
        this.selectedProductId = selectedProductId;
    }

    public SpecialPriceDiscount getSpecialPriceDiscount()
    {
        if (specialPriceDiscount == null) {
            specialPriceDiscount = new SpecialPriceDiscount();
        }
        return specialPriceDiscount;
    }

    public void addQuantityDiscount()
    {
        if (selectedProduct.getDiscounts() == null) {
            selectedProduct.setDiscounts(new ArrayList<Discount>());
        }
        if (selectedProduct.getId() == null) {
            messages.info(BundleKeys.PRODUCT_NOT_PERSISTED);
        } else {
            if (quantityDiscount.getPercentage().compareTo(new BigDecimal(MAX_DISCOUNT)) == 1) {
                messages.info(BundleKeys.QUANTITY_DISCOUNT_WARNING);
            } else if (validateDiscountDates(quantityDiscount.getStartDate(), quantityDiscount.getEndDate())) {
                quantityDiscount.setProduct(productHome.getInstance());
                selectedProduct.getDiscounts().add(getQuantityDiscount());
                discountQuantityList.add(getQuantityDiscount());
                quantityDiscount = null;
                messages.info(BundleKeys.DISCOUNT_ADDED);
                productHome.update();
                messages.info(BundleKeys.PRODUCT_UPDATED);
            } else {
                messages.error(BundleKeys.DATE_WARNING);
            }
        }
    }

    public void addSpecialPrice()
    {
        if (selectedProduct.getDiscounts() == null) {
            selectedProduct.setDiscounts(new ArrayList<Discount>());
        }
        if (selectedProduct.getId() == null) {
            messages.info(BundleKeys.PRODUCT_NOT_PERSISTED);
        } else {
            if (validateDiscountDates(specialPriceDiscount.getStartDate(), specialPriceDiscount.getEndDate())) {
                specialPriceDiscount.setProduct(productHome.getInstance());
                selectedProduct.getDiscounts().add(getSpecialPriceDiscount());
                discountSpecialList.add(getSpecialPriceDiscount());
                specialPriceDiscount = null;
                messages.info(BundleKeys.DISCOUNT_ADDED);
                productHome.update();
                messages.info(BundleKeys.PRODUCT_UPDATED);
            } else {
                messages.error(BundleKeys.DATE_WARNING);
            }
        }
    }

    public void initConversation()
    {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void initView()
    {
        this.selectedProduct = productHome.getInstance();
        if (selectedProduct.getCategory() != null) {
            this.selectedParentCategory = selectedProduct.getCategory().getParentCategory();
        }
        for (Tag tag : selectedProduct.getTags()) {
            productTags += tag.getName() + ", ";
        }

        this.productPhotos = selectedProduct.getFiles();
        if (this.productPhotos == null) {
            this.productPhotos = new ArrayList<ProductFile>();
        }

        initConversation();
    }

    public boolean isDiscountExpired(Discount discount)
    {
        if (discount instanceof QuantityDiscount) {

            QuantityDiscount quantityDiscount1 = (QuantityDiscount) discount;
            if (discountProcessor.validateDates(quantityDiscount1)) {
                return false;
            }
        } else if (discount instanceof SpecialPriceDiscount) {

            SpecialPriceDiscount specialPriceDiscount1 = (SpecialPriceDiscount) discount;
            if (discountProcessor.validateDates(specialPriceDiscount1)) {
                return false;
            }
        }
        return true;
    }

    public void markAsThumbnail(ProductFile productFile)
    {
        try {
            selectedProduct.setThumbnail(imageScaling.scale(productFile.getContent(), THUMBNAIL_SIZE, THUMBNAIL_SIZE));
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        messages.info(BundleKeys.MARKED_AS_THUMBNAIL);
    }

    public void photoUploaded(FileUploadEvent event) throws IOException
    {
        Photo photo = new Photo();
        byte[] data = event.getUploadedFile().getData();

        photo.setContent(imageScaling.scale(data, PHOTO_SIZE, PHOTO_SIZE));
        photo.setName("NAME");
        if (productPhotos.size() >= MAX_FILES_UPLOAD) {
            fileUploadMessage = messages.warn(BundleKeys.MAX_FILES_WARNING).build().getText();
        } else {
            if (data.length >= MAX_FILE_SIZE) {
                fileUploadMessage = messages.warn(BundleKeys.MAX_SIZE_WARNING).build().getText();
            } else {
                productPhotos.add(photo);
                fileUploadMessage = messages.info(BundleKeys.FILE_UPLOADED).build().getText();
            }
        }
    }

    public void processTags()
    {
        final Iterable<String> splitTags = Splitter.on(",").trimResults().omitEmptyStrings().split(productTags);

        final Set<String> splittedSetOfTags = Sets.newHashSet(splitTags);

        Set<Tag> newTags = Sets.newHashSet(tagHome.getExistingTags(splittedSetOfTags));

        for (Tag tag : newTags) {
            if (splittedSetOfTags.contains(tag.getName())) {
                splittedSetOfTags.remove(tag.getName());
            }
        }
        for (String tag : splittedSetOfTags) {
            newTags.add(new Tag(tag));
        }

        selectedProduct.getTags().removeAll(Sets.difference(Sets.newHashSet(selectedProduct.getTags()), newTags));
        selectedProduct.getTags().addAll(Sets.difference(newTags, Sets.newHashSet(selectedProduct.getTags())));
    }

    public void removeDiscount(Discount discount)
    {
        selectedProduct.getDiscounts().remove(discount);
    }

    public void removePhoto(ProductFile productFile)
    {
        productPhotos.remove(productFile);
        messages.info(BundleKeys.FILE_REMOVED);
    }

    public void removeQuantityDiscount(QuantityDiscount discount)
    {
        discountQuantityList.remove(discount);
        removeDiscount(discount);
        productHome.update();
        messages.info(BundleKeys.DISCOUNT_REMOVED);
    }

    public void removeSpecialDiscount(SpecialPriceDiscount discount)
    {
        discountSpecialList.remove(discount);
        removeDiscount(discount);
        productHome.update();
        messages.info(BundleKeys.DISCOUNT_REMOVED);
    }

    public String save()
    {

        if (selectedProduct.getDiscounts() == null) {
            selectedProduct.setDiscounts(new ArrayList<Discount>());
        }
        for (Discount discount : selectedProduct.getDiscounts()) {
            discount.setProduct(selectedProduct);
        }
        if (productTags != null && !productTags.isEmpty()) {
            processTags();
        } else {
            selectedProduct.getTags().clear();
        }
        if (productPhotos.isEmpty()) {
            messages.error(BundleKeys.PHOTOS_ERROR);
            return Constants.OUTCOME_FAILURE;
        }
        selectedProduct.setFiles(productPhotos);
        if (selectedProduct.getId() == null) {
            try {
                selectedProduct.setThumbnail(imageScaling.scale(productPhotos.get(0).getContent(), THUMBNAIL_SIZE, THUMBNAIL_SIZE));
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
            productHome.persist();
            messages.info(BundleKeys.PRODUCT_ADDED);
        } else {
            productHome.update();
            messages.info(BundleKeys.PRODUCT_UPDATED);
        }
        userHome.setId(currentUser.getId());
        selectedShop.getProducts().add(selectedProduct);

        return Constants.OUTCOME_SUCCESS;
    }

    private boolean validateDiscountDates(Date startDate, Date endDate)
    {
        LocalDate startDateJ = new LocalDate(startDate);
        LocalDate endDateJ = new LocalDate(endDate);
        LocalDate currentDateJ = new LocalDate();
        return (startDateJ.isBefore(endDateJ) || startDateJ.isEqual(endDateJ)) && (startDateJ.isAfter(currentDateJ) || startDateJ.isEqual(currentDateJ));
    }
}
