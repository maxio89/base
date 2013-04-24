package pl.itcrowd.base.domain;

import java.util.Map;

public interface Translatable<E> {
// -------------------------- OTHER METHODS --------------------------

    Map<Language, E> getTranslations();
}
