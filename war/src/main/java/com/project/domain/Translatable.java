package com.project.domain;

import com.project.language.Language;

import java.util.Map;

public interface Translatable<E> {
// -------------------------- OTHER METHODS --------------------------

    Map<Language, E> getTranslations();
}
