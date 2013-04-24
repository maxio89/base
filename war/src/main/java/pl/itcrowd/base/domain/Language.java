package pl.itcrowd.base.domain;

import org.hibernate.validator.constraints.Length;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "LANGUAGE", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE___LANGUAGE___ISO_639_1", columnNames = "ISO_639_1"),
    @UniqueConstraint(name = "UNIQUE___LANGUAGE___ISO_639_2", columnNames = "ISO_639_2")})
public class Language implements Serializable, Identifiable<Long> {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue(generator = "LANGUAGE_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "LANGUAGE_ID_SEQUENCE", sequenceName = "LANGUAGE_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Length(min = 2, max = 2)
    @Column(name = "ISO_639_1", nullable = false, length = 2)
    private String iso6391;

    @NotNull
    @Length(min = 3, max = 3)
    @Column(name = "ISO_639_2", nullable = false, length = 3)
    private String iso6392;

//    @MapKey(name = "language")
//    @OneToMany(mappedBy = "translatedObject", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Map<Language, LanguageTranslation> translations;

// --------------------- GETTER / SETTER METHODS ---------------------

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getIso6391()
    {
        return iso6391;
    }

    public void setIso6391(String iso6391)
    {
        this.iso6391 = iso6391;
    }

    public String getIso6392()
    {
        return iso6392;
    }

    public void setIso6392(String iso6392)
    {
        this.iso6392 = iso6392;
    }

//    public Map<Language, LanguageTranslation> getTranslations()
//    {
//        if (translations == null) {
//            translations = new HashMap<Language, LanguageTranslation>();
//        }
//        return translations;
//    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }

        Language language = (Language) o;
        //We must use getter because classes are being proxied sometimes
        return !(getId() != null ? !getId().equals(language.getId()) : language.getId() != null);
    }

    @Override
    public int hashCode()
    {
        //We must use getter because classes are being proxied sometimes
        return getId() != null ? getId().hashCode() : 0;
    }
}
