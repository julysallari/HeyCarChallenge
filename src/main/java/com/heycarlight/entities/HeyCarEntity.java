package com.heycarlight.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class HeyCarEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    public HeyCarEntity() {}

    public HeyCarEntity(@Nullable UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
