package com.bankofspring.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents all common properties of standard domain objects
 *
 * @author Arpit Khandelwal
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseDomainObject implements Serializable {

    @CreationTimestamp
    protected Date createTimestamp = new Date();

    @UpdateTimestamp
    protected Date lastEditTimestamp;
}
