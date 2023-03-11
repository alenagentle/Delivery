package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.dto.request.ChangePasswordRequest;
import com.guavapay.delivery.dto.response.UserResponse;
import com.guavapay.delivery.entity.Role;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.RoleName;
import com.guavapay.delivery.helper.RoleHelper;
import com.guavapay.delivery.helper.UserHelper;
import com.guavapay.delivery.mapper.UserMapper;
import com.guavapay.delivery.repository.UserRepository;
import com.guavapay.delivery.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final RoleHelper roleHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<UserResponse> findAllUsers() {
        List<UserData> userDataList = userRepository.findAll();
        return userMapper.mapToResponses(userDataList);
    }

    @Override
    @Transactional
    public UserResponse setCourierRole(String email) {
        UserData user = userHelper.findUserByEmail(email);
        Role role = roleHelper.findRoleByRoleName(RoleName.ROLE_COURIER);
        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        } else {
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        UserData user = userHelper.findUserByEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedPassword);
        userHelper.saveUser(user);
        log.info("Password changed successfully");
    }

}
