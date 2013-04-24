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
    BundleKey PHOTOS_ERROR = new BundleKey(BundleNames.view.name(), "view.product.photosWarning");
    BundleKey MAX_FILES_WARNING = new BundleKey(BundleNames.view.name(), "view.product.maxFilesWarning");
    BundleKey MAX_SIZE_WARNING = new BundleKey(BundleNames.view.name(), "view.product.maxSizeWarning");
    BundleKey MAX_SIZE_WARNING_AVATAR = new BundleKey(BundleNames.view.name(), "view.psychic.maxSizeWarning");
    BundleKey FILE_UPLOADED = new BundleKey(BundleNames.view.name(), "CRUD.files.fileUploaded");
    BundleKey FILE_REMOVED = new BundleKey(BundleNames.view.name(), "CRUD.files.fileRemoved");
    BundleKey DUPLICATE_COUNTRY = new BundleKey(BundleNames.view.name(), "view.country.duplicateWarning");
    BundleKey COUNTRY_UPDATE = new BundleKey(BundleNames.view.name(), "view.country.countryUpdate");
    BundleKey COUNTRY_ADD = new BundleKey(BundleNames.view.name(), "view.country.countryAdd");
    BundleKey COUNTRY_DELETE = new BundleKey(BundleNames.view.name(), "view.country.countryDelete");
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
}
