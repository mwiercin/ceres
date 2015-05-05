package net.mawi.ceres.test;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import net.mawi.ceres.model.Car;
import net.mawi.ceres.model.CarMake;
import net.mawi.ceres.model.CarMakeRepository;
import net.mawi.ceres.model.CarModel;
import net.mawi.ceres.model.CarModelRepository;
import net.mawi.ceres.model.CarRepository;
import net.mawi.ceres.model.Role;
import net.mawi.ceres.model.RoleRepository;
import net.mawi.ceres.model.User;
import net.mawi.ceres.model.UserRepository;

public class InitBean {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private CarRepository carRepository;

    @Resource
    private CarModelRepository carModelRepository;

    @Resource
    private CarMakeRepository carMakeRepository;

    @PostConstruct
    public void init() {
        Role userRole = Role.getBuilder("CRS_USER", "User").build();
        Role adminRole = Role.getBuilder("CRS_ADMIN", "Admin").build();
        userRole = roleRepository.save(userRole);
        adminRole = roleRepository.save(adminRole);

        User user1 = User.getBuilder("test1", "test1").build();
        User user2 = User.getBuilder("test2", "test2").build();
        User admin = User.getBuilder("admin", "admin").build();
        user1.getRoles().add(userRole);
        admin.getRoles().add(userRole);
        admin.getRoles().add(adminRole);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(admin);

        CarMake make = CarMake.getBuilder("Jaguar").build();
        make = carMakeRepository.save(make);
        CarModel model = CarModel.getBuilder("X-Type", make).build();
        model = carModelRepository.save(model);
        Car jag = Car.getBuilder("vin", "", make, model).build();
        jag = carRepository.save(jag);

        admin.addCar(jag);
        userRepository.save(admin);
        carRepository.save(jag);
    }
}
