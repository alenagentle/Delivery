package com.guavapay.delivery.service.impl;

import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.helper.UserHelper;
import com.guavapay.delivery.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserHelper userHelper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userHelper.findUserByEmail(username);
        return userMapper.mapToUserDetails(user);
    }
}
