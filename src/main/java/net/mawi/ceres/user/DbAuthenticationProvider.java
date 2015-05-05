package net.mawi.ceres.user;

import java.util.Collection;
import javax.annotation.Resource;
import net.mawi.ceres.model.Role;
import net.mawi.ceres.model.User;
import net.mawi.ceres.model.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class DbAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) a;
        String login = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());
        User user = userRepository.findUserByLogin(login);
        if (null != user && user.verifyPassword(password)) {
            Collection<Role> auths = user.getRoles();
            return new UsernamePasswordAuthenticationToken(login, null, auths);
        }
        throw new BadCredentialsException("Bad login or password");
    }

    @Override
    public boolean supports(Class<?> type) {
        return UsernamePasswordAuthenticationToken.class.equals(type);
    }

}
