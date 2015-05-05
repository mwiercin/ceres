package net.mawi.ceres.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "car_makers")
public class CarMake implements Serializable {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String MODELS_COLUMN = "models";

    @Id
    @Column(name = ID_COLUMN, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = NAME_COLUMN)
    private String name;

    @Column(name = MODELS_COLUMN)
    @OneToMany(mappedBy = "make")
    private Collection<CarModel> models;

    protected CarMake() {
    }

    private CarMake(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<CarModel> getModels() {
        return models;
    }

    public static Builder getBuilder(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private final CarMake built;

        Builder(String name) {
            built = new CarMake(name);
        }

        public CarMake build() {
            return built;
        }
    }

}
