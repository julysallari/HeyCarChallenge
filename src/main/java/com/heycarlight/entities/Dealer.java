package com.heycarlight.entities;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;

@Entity
public class Dealer extends HeyCarEntity {

    private String name;

    private Dealer(){}

    public Dealer(@NonNull String name) {
        super();
        this.name = name;
    }
}
