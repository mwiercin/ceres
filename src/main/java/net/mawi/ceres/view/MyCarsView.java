package net.mawi.ceres.view;

import javax.annotation.Resource;
import net.mawi.ceres.model.Car;
import net.mawi.ceres.model.User;
import net.mawi.ceres.model.UserRepository;

public class MyCarsView {

    @Resource
    private UserRepository userRepository;

    public String getCars() {
        StringBuilder sb = new StringBuilder();
        User u = userRepository.findUserByLogin("admin");
        for (Car c : u.getCars()) {
            sb.append(c);
            sb.append(";");
        }
        return sb.toString();
    }
}
