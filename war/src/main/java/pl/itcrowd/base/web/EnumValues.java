package pl.itcrowd.base.web;

import pl.itcrowd.base.domain.RoleEnum;

import javax.enterprise.inject.Model;

/**
 * Enums for naming items in selectOneMenu on Marketplace page.
 */
@SuppressWarnings("UnusedDeclaration")
@Model
public class EnumValues {

    public RoleEnum[] roleEnums()
    {
        return RoleEnum.values();
    }
}
