package com.bankofspring.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Customer extends BaseDomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long customerId;

    @Column(unique = true)
    private String ssn;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "accountOwner")
    private Set<Account> accounts;

    private String name;
    private String address1;
    private String address2;
    private String city;
    private String contactNumber;
}
