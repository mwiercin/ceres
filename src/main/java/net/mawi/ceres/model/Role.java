package net.mawi.ceres.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements Serializable, GrantedAuthority {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";

    @Id
    @Column(name = ID_COLUMN, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = NAME_COLUMN, nullable = false, unique = true)
    private String name;

    @Column(name = DESCRIPTION_COLUMN)
    private String description;

    protected Role() {
    }

    private Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public static Builder getBuilder(String name, String description) {
        return new Builder(name, description);
    }

    public static class Builder {

        private final Role built;

        Builder(String name, String description) {
            built = new Role(name, description);
        }

        public Role build() {
            return built;
        }
    }
}
