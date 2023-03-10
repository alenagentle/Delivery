package com.guavapay.delivery.helper;

import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class UserHelper {

    private final UserRepository userRepository;

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserData saveUser(UserData user) {
        return userRepository.save(user);
    }

    public UserData findUserByEmail(String email) {
        return userRepository.findUserDataByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email '%s' not found", email)));
    }

    public UserData getCurrentUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String anonymousUser = "anonymousUser";
        if (authentication.getPrincipal() == anonymousUser) {
            return null;
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String email = principal.getUsername();
        return findUserByEmail(email);
    }

}
