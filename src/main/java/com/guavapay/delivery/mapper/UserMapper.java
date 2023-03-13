package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.SignUpRequest;
import com.guavapay.delivery.dto.response.UserResponse;
import com.guavapay.delivery.entity.Role;
import com.guavapay.delivery.entity.UserData;
import com.guavapay.delivery.entity.enums.RoleName;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserData mapToUser(SignUpRequest signupRequest);

    @Mapping(target = "roles", ignore = true)
    public abstract UserResponse mapToResponse(UserData userData);

    public abstract List<UserResponse> mapToResponses(List<UserData> userDataList);

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

    @AfterMapping
    protected void map(@MappingTarget UserResponse response, UserData user) {
        List<RoleName> roles = user.getRoles().stream().map(Role::getName).toList();
        response.setRoles(roles);
    }

}
