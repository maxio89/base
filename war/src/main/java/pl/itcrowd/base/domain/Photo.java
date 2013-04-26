package pl.itcrowd.base.domain;

import org.apache.commons.codec.binary.Base64;
import pl.itcrowd.base.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PHOTO")
public class Photo extends ProductFile {

    //    ------------ FIELDS --------------------------

    @NotNull
    @Size(min = 1, max = 300000)
    @Column(name = "THUMBNAIL", length = 300000)
    private byte[] thumbnail;

    // --------------------------- CONSTRUCTORS ---------------------------


    //    ------------------ GETTERS & SETTERS ----------------------------

    public byte[] getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailToBase64()
    {
        return thumbnail != null ? Constants.BASE64_PREFIX + Base64.encodeBase64String(thumbnail) : null;
    }
}
