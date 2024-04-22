package com.zinoveva.planer.domain;

import jakarta.persistence.Embeddable;

/**
 * Статус
 */
@Embeddable
public enum Status {
    /**
     * активен
     */
    active,

    /**
     * закрыт
     */
    close,

    /**
     * создан
     */
    create

}
