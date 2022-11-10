package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value // Like @Data but with final fields
public class SimpleValueModel<F extends Comparable<F>> implements ValueModel<F> {
    Class<F> type;
    F value;

    @SuppressWarnings("unchecked")
    public static <F extends Comparable<F>>
    SimpleValueModel<F> from(Class<? extends Comparable<?>> type, F value) {
        return new SimpleValueModel<>((Class<F>) type, value);
    }

    @SuppressWarnings("unchecked")
    public static <F extends Comparable<F>>
    SimpleValueModel<F> from(@NonNull F value) {
        return new SimpleValueModel<>((Class<F>) value.getClass(), value);
    }

}
