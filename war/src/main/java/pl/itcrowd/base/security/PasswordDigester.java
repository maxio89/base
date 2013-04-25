package pl.itcrowd.base.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Class for data encryption
 */
public class PasswordDigester implements Serializable {
// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public String getDigest(@Nonnull String password)
    {
        return DigestUtils.md5Hex(password);
    }
}
