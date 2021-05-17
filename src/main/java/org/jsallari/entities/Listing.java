package org.jsallari.entities;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public Listing() {}

    public Listing(@Nullable Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
