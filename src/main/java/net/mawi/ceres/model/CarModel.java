package net.mawi.ceres.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "car_models")
public class CarModel implements Serializable {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String MAKE_COLUMN = "make";

    @Id
    @Column(name = ID_COLUMN, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = NAME_COLUMN)
    private String name;

    @ManyToOne
    private CarMake make;

    public CarModel() {
    }

    private CarModel(String name, CarMake make) {
        this.name = name;
        this.make = make;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CarMake getMake() {
        return make;
    }

    public static Builder getBuilder(String name, CarMake make) {
        return new Builder(name, make);
    }

    public static class Builder {

        private final CarModel built;

        Builder(String name, CarMake make) {
            built = new CarModel(name, make);
        }

        public CarModel build() {
            return built;
        }
    }

}
