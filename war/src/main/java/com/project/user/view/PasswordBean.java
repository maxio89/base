package com.project.user.view;

import org.apache.commons.collections.comparators.NullComparator;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

public class PasswordBean {

    private final NullComparator nullComparator = new NullComparator();

    private String password;
    private String passwordConfirmation;

    @NotNull
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @NotNull
    public String getPasswordConfirmation()
    {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation)
    {
        this.passwordConfirmation = passwordConfirmation;
    }

    @AssertTrue(message = "{com.project.view.RegisterView.passwordsDontMatch}")
    public boolean isPasswordMatch(){
        return 0==nullComparator.compare(password, passwordConfirmation);
    }
}
