package com.project.security;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

/**
 * Class for data encryption
 */
public class PasswordDigester implements Serializable {
// -------------------------- OTHER METHODS --------------------------

    public String getDigest(String password)
    {
        return DigestUtils.md5Hex(password);
    }
}
