package org.uranium.payments.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Payment {

    @ManyToOne
    private User user;
    private MONTH month;
    private int year;
}
