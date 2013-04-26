package pl.itcrowd.base.web;

import org.jboss.seam.international.status.builder.BundleKey;

public interface BundleKeys {

    // ------------------------------ FIELDS ------------------------------
    BundleKey AUTHORIZATION_EXCEPTION = new BundleKey(BundleNames.view.name(), "view.denied.authorizationException");
    BundleKey DATA_CANNOT_BE_SAVED = new BundleKey(BundleNames.business.name(), "business.dataCannotBeSaved");
    BundleKey DATA_SAVED_SUCCSSFULLY = new BundleKey(BundleNames.business.name(), "business.dataSavedSuccessfully");
    BundleKey MESSAGE_SENT_SUCCESSFULLY = new BundleKey(BundleNames.business.name(), "business.messageSentSuccessfully");
    BundleKey DELETE_FOREIGN_KEY_VIOLATION = new BundleKey(BundleNames.validation.name(), "validation.deleteForeignKeyViolation");
    BundleKey ENTITY_NOT_FOUND = new BundleKey(BundleNames.business.name(), "business.entityNotFound");
    BundleKey NO_ELEMENT_SELECTED_WARNING = new BundleKey(BundleNames.business.name(), "business.noElementSelectedWarning");
    BundleKey SELECTED_ELEMENTES_REMOVED_SUCCESSFULLY = new BundleKey(BundleNames.business.name(), "business.selectedElementsRemovedSuccessfully");
    BundleKey SESSION_TIMEOUT = new BundleKey(BundleNames.business.name(), "business.sessionTimeout");
    BundleKey TECHNICAL_ERROR = new BundleKey(BundleNames.business.name(), "business.technicalError");
    BundleKey REGISTER_EMAIL_SUBJECT = new BundleKey(BundleNames.view.name(), "mail.registerEmailSubject");
    BundleKey EMAIL_ALREADY_REGISTERED = new BundleKey(BundleNames.view.name(), "mail.emailAlreadyRegistered");
    BundleKey EMAIL_NOT_FOUND = new BundleKey(BundleNames.view.name(), "view.remindPassword.emailNotFound");
    BundleKey RESET_PASSWORD_EMAIL_SUBJECT = new BundleKey(BundleNames.view.name(), "mail.remindPassword.emailSubject");
    BundleKey RESET_PASSWORD_EMAIL_SENT = new BundleKey(BundleNames.view.name(), "view.remindPassword.linkSent");
    BundleKey RESET_PASSWORD_TOKEN_EXISTS = new BundleKey(BundleNames.view.name(), "view.remindPassword.tokenAlreadyExists");
    BundleKey RESET_PASSWORD_WRONG_ACTIVATION_LINK = new BundleKey(BundleNames.view.name(), "view.forgottenPassword.wrongResetLink");
    BundleKey ROLE_ALREADY_EXISTS = new BundleKey(BundleNames.view.name(), "view.roleDetails.roleAlreadyExists");
    BundleKey PDF_GENERATION_FAILURE = new BundleKey(BundleNames.business.name(), "business.pdfGenerationFailure");
    BundleKey LICENSE_AGREEMENT_NOT_ACCEPTED = new BundleKey(BundleNames.view.name(), "view.register.licenseNotAccepted");
    BundleKey WRONG_PASSWORD_DATA = new BundleKey(BundleNames.view.name(), "view.register.wrongPasswordData");
    BundleKey PASSWORD_CHANGE_SUCCESFULLY = new BundleKey(BundleNames.view.name(), "view.changePassword.successfulChange");
    BundleKey PROFILE_UPDATED = new BundleKey(BundleNames.view.name(), "view.userProfileDetails.editStatusSuccess");
    BundleKey PASSWORDS_DONT_MATCH = new BundleKey(BundleNames.view.name(), "view.changePassword.passwordsDontMatch");
    BundleKey RESET_PASSWORD_SUCCESSFUL = new BundleKey(BundleNames.view.name(), "view.changePassword.successful");
    BundleKey RESET_PASSWORD_FAILURE = new BundleKey(BundleNames.view.name(), "view.changePassword.failure");
    BundleKey ACTIVATION_MAIL_RESENT = new BundleKey(BundleNames.view.name(), "view.resendToken.activationMailResent");
    BundleKey EMAIL_NOT_WELL_FORMED = new BundleKey(BundleNames.view.name(), "view.resendToken.emailNotWellFormed");
    BundleKey ACTIVATION_MAIL_RESENT_ERROR = new BundleKey(BundleNames.view.name(), "view.resendToken.activationMailResentError");
    BundleKey MAIL_DEAR_USER_HEADER = new BundleKey(BundleNames.view.name(), "mail.dearUserHeader");
    BundleKey MAIL_DEFAULT_USER_NAME = new BundleKey(BundleNames.view.name(), "mail.defaultUserName");
    BundleKey MAIL_REGISTRATION_THANKS = new BundleKey(BundleNames.view.name(), "mail.registration.thanks");
    BundleKey MAIL_REGISTRATION_ACTIV_LINK_INFO = new BundleKey(BundleNames.view.name(), "mail.registration.activationLinkInfo");
    BundleKey MAIL_RESET_PASS_THANKS = new BundleKey(BundleNames.view.name(), "mail.resetPass.thanks");
    BundleKey MAIL_RESET_PASS_ACTIV_LINK_INFO = new BundleKey(BundleNames.view.name(), "mail.resetPass.activationLinkInfo");
    BundleKey MAIL_THANK_YOU_TXT = new BundleKey(BundleNames.view.name(), "mail.thankYouText");
    BundleKey MAIL_FIRM_NAME = new BundleKey(BundleNames.view.name(), "mail.firmName");

//    ------------- SHOP MODULE ----------------------
    BundleKey MARKED_AS_THUMBNAIL = new BundleKey(BundleNames.view.name(), "view.product.markedAsThumbnail");
    BundleKey PHOTOS_ERROR = new BundleKey(BundleNames.view.name(), "view.product.photosWarning");
    BundleKey PRICE_UPDATED = new BundleKey(BundleNames.view.name(), "view.userProfileDetails.priceUpdated");
    BundleKey CART_ITEM_REMOVED = new BundleKey(BundleNames.view.name(), "view.cart.itemRemoved");
    BundleKey CART_ITEM_ADDED = new BundleKey(BundleNames.view.name(), "view.cart.itemAdded");
    BundleKey CART_ITEM_CHANGED_QUANTITY = new BundleKey(BundleNames.view.name(), "view.cart.itemChangedQuantity");
    BundleKey DIFFERENT_SELLER_FOR_ORDER = new BundleKey(BundleNames.view.name(), "view.cart.differentSellerForOrder");
    BundleKey CART_CLEARED = new BundleKey(BundleNames.view.name(), "view.cart.cartCleared");
    BundleKey EMPTY_CART_ERROR = new BundleKey(BundleNames.view.name(), "view.checkout.emptyCartError");
    BundleKey ORDER_STATUS_CHANGED = new BundleKey(BundleNames.view.name(), "view.orderDetails.orderStatusChanged");
    BundleKey ORDER_STATUS_CHANGED_ERROR = new BundleKey(BundleNames.view.name(), "view.orderDetails.orderStatusChangedFailure");
    BundleKey PRODUCT_REMOVED = new BundleKey(BundleNames.view.name(), "view.product.productRemoved");
    BundleKey PRODUCT_UPDATED = new BundleKey(BundleNames.view.name(), "view.product.productUpdated");
    BundleKey PRODUCT_NOT_PERSISTED = new BundleKey(BundleNames.view.name(), "view.product.productNotPersisted");
    BundleKey PRODUCT_ADDED = new BundleKey(BundleNames.view.name(), "view.product.productAdded");
    BundleKey MAX_FILES_WARNING = new BundleKey(BundleNames.view.name(), "view.product.maxFilesWarning");
    BundleKey MAX_SIZE_WARNING = new BundleKey(BundleNames.view.name(), "view.product.maxSizeWarning");
    BundleKey MAX_SIZE_WARNING_AVATAR = new BundleKey(BundleNames.view.name(), "view.psychic.maxSizeWarning");
    BundleKey FILE_UPLOADED = new BundleKey(BundleNames.view.name(), "CRUD.files.fileUploaded");
    BundleKey FILE_REMOVED = new BundleKey(BundleNames.view.name(), "CRUD.files.fileRemoved");
    BundleKey DISCOUNT_ADDED = new BundleKey(BundleNames.view.name(), "view.product.discount.added");
    BundleKey QUANTITY_DISCOUNT_WARNING = new BundleKey(BundleNames.view.name(), "view.product.discount.discountWarning");
    BundleKey DATE_WARNING = new BundleKey(BundleNames.view.name(), "view.product.discount.dateWarning");
    BundleKey DISCOUNT_REMOVED = new BundleKey(BundleNames.view.name(), "view.product.discount.removed");
    BundleKey NO_CATEGORY_SELECTED = new BundleKey(BundleNames.view.name(), "view.product.category.selectedWarning");
    BundleKey DUPLICATE_CATEGORY = new BundleKey(BundleNames.view.name(), "view.product.category.duplicateWarning");
    BundleKey CATEGORY_ADD = new BundleKey(BundleNames.view.name(), "view.product.category.categoryAdd");
    BundleKey CATEGORY_UPDATE = new BundleKey(BundleNames.view.name(), "view.product.category.categoryUpdate");
    BundleKey CATEGORY_DELETE = new BundleKey(BundleNames.view.name(), "view.product.category.categoryDelete");
    BundleKey DUPLICATE_COUNTRY = new BundleKey(BundleNames.view.name(), "view.country.duplicateWarning");
    BundleKey COUNTRY_UPDATE = new BundleKey(BundleNames.view.name(), "view.country.countryUpdate");
    BundleKey COUNTRY_ADD = new BundleKey(BundleNames.view.name(), "view.country.countryAdd");
    BundleKey COUNTRY_DELETE = new BundleKey(BundleNames.view.name(), "view.country.countryDelete");
    BundleKey ORDER_CHARGING_ERROR = new BundleKey(BundleNames.view.name(), "view.cart.checkout.chargingError");
    BundleKey CHECKOUT_NOT_ENOUGH_CREDITS = new BundleKey(BundleNames.view.name(), "view.cart.checkout.notEnoughCredits");
    BundleKey ORDER_CHARGED = new BundleKey(BundleNames.view.name(), "view.cart.checkout.orderCharged");
    BundleKey ORDER_STATUS_CHANGED_SUBJECT = new BundleKey(BundleNames.view.name(), "view.order.statusChangedSubject");
    BundleKey ORDER_STATUS_CHANGED_INFO = new BundleKey(BundleNames.view.name(), "view.order.info");
    BundleKey ORDER_STATUS_CHANGED_CURR_LABEL = new BundleKey(BundleNames.view.name(), "view.order.currentStatus");
    BundleKey ORDER_STATUS_CHANGED_ORDER_INFO = new BundleKey(BundleNames.view.name(), "view.order.orderInfo");
    BundleKey PRODUCT_QUANTITY_ISNT_VALID = new BundleKey(BundleNames.view.name(), "view.checkout.productQuantityIsNotValid");
    BundleKey CART_QUANTITY_TO_HIGH = new BundleKey(BundleNames.view.name(), "view.cart.quantityToHigh");
    BundleKey WRONG_QUANTITY_VALUE = new BundleKey(BundleNames.view.name(), "view.cart.wrongQuantityValue");
    BundleKey MAX_QUANTITY_REACHED_ERROR = new BundleKey(BundleNames.view.name(), "view.cart.maxProductQuantityReached");

//    ------------- END MODULE -----------------------
}
