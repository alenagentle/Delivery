package com.guavapay.delivery.helper;

import com.guavapay.delivery.entity.Role;
import com.guavapay.delivery.entity.enums.RoleName;
import com.guavapay.delivery.exception.NotFoundException;
import com.guavapay.delivery.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class RoleHelper {

    private final RoleRepository roleRepository;

    public Role findRoleByRoleName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role with name " + roleName.name() + " not found"));
    }


}
