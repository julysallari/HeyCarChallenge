package heycarlight.entities;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    public Listing() {}

    public Listing(@Nullable UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
