package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.entity.UserData;
import org.mapstruct.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class  UserMapper {

    public abstract UserData mapToUser(SignUpRequest signupRequest);

    public UserDetails mapToUserDetails(UserData user) {
        List<String> roles = user.getRoles().stream()
                .map(r -> r.getName().name())
                .toList();

        List<String> authorities = user.getRoles().stream()
                .flatMap(r -> r.getAuthorities().stream())
                .map(a -> a.getName().name())
                .collect(Collectors.toList());

        authorities.addAll(roles);

        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }

}
