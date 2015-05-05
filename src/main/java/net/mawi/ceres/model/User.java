package net.mawi.ceres.model;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.apache.commons.lang.RandomStringUtils;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public static final String ID_COLUMN = "id";
    public static final String LOGIN_COLUMN = "login";
    public static final String HASH_COLUMN = "password_hash";
    public static final String SALT_COLUMN = "salt";

    @Id
    @Column(name = ID_COLUMN, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = LOGIN_COLUMN, nullable = false, unique = true)
    private String login;

    @Column(name = HASH_COLUMN, nullable = false)
    private String passwordHash;

    @Column(name = SALT_COLUMN, nullable = false)
    private String salt;

    @JoinTable(name = "user_roles",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = User.ID_COLUMN)
            },
            inverseJoinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = Role.ID_COLUMN)
            }
    )
    @ManyToMany
    private final Collection<Role> roles = new ArrayList<>();

    @JoinTable(name = "user_cars",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = User.ID_COLUMN)
            },
            inverseJoinColumns = {
                @JoinColumn(name = "car_id", referencedColumnName = Car.ID_COLUMN)
            }
    )
    @ManyToMany
    private final Collection<Car> cars = new ArrayList<>();

    protected User() {
    }

    private User(String login, String password) {
        this.login = login;
        this.passwordHash = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public ImmutableCollection<Car> getCars() {
        return ImmutableSet.copyOf(cars);
    }

    public void updatePassword(String newPassword) {
        this.salt = RandomStringUtils.random(8);
        try {
            this.passwordHash = getPasswordHash(newPassword, salt);
        } catch (NoSuchAlgorithmException ex) {
            //should never happen
        }
    }

    private static String getPasswordHash(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((salt + password).getBytes());

        byte[] byteData = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public boolean verifyPassword(String password) {
        try {
            return this.passwordHash.equals(getPasswordHash(password, salt));
        } catch (NoSuchAlgorithmException ex) {
            //should never happen
            return false;
        }
    }

    public void addCar(Car car) {
        if (!cars.contains(car)) {
            cars.add(car);
            car.addOwner(this);
        }
    }

    public void removeCar(Car car) {
        if (cars.contains(car)) {
            cars.remove(car);
            car.removeOwner(this);
        }
    }

    public static Builder getBuilder(String login, String password) {
        return new Builder(login, password);
    }

    public static class Builder {

        private final User built;

        Builder(String login, String password) {
            built = new User(login, "");
            built.updatePassword(password);
        }

        public User build() {
            return built;
        }
    }
}
