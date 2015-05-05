package net.mawi.ceres.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car implements Serializable {

    public static final String ID_COLUMN = "id";
    public static final String VIN_COLUMN = "vin";
    public static final String REGNR_COLUMN = "regnr";
    public static final String FIRSTREG_COLUMN = "firstreg";
    public static final String MAKE_COLUMN = "make";
    public static final String MODEL_COLUMN = "model";

    @Id
    @Column(name = ID_COLUMN, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = VIN_COLUMN, nullable = false, unique = true)
    private String vin;

    @Column(name = REGNR_COLUMN)
    private String regnr;

    @ManyToOne
    private CarMake make;

    @ManyToOne
    private CarModel model;

    @ManyToMany(mappedBy = "cars")
    private final Collection<User> owners = new ArrayList<>();

    protected Car() {
    }

    public Car(String vin, String regnr, CarMake make, CarModel model) {
        this.vin = vin;
        this.regnr = regnr;
        this.make = make;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public ImmutableCollection<User> getOwners() {
        return ImmutableSet.copyOf(owners);
    }

    public String getRegnr() {
        return regnr;
    }

    public CarMake getMake() {
        return make;
    }

    public CarModel getModel() {
        return model;
    }

    public void addOwner(User user) {
        if (!owners.contains(user)) {
            this.owners.add(user);
            user.addCar(this);
        }
    }

    public void removeOwner(User user) {
        if (owners.contains(user)) {
            owners.remove(user);
            user.removeCar(this);
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add(ID_COLUMN, id).add(VIN_COLUMN, vin).add(REGNR_COLUMN, regnr)
                .add(MAKE_COLUMN, make.getName()).add(MODEL_COLUMN, model.getName()).toString();
    }

    public static Builder getBuilder(String vin, String regnr, CarMake make, CarModel model) {
        return new Builder(vin, regnr, make, model);
    }

    public static class Builder {

        private final Car built;

        Builder(String vin, String regnr, CarMake make, CarModel model) {
            built = new Car(vin, regnr, make, model);
        }

        public Car build() {
            return built;
        }
    }
}
