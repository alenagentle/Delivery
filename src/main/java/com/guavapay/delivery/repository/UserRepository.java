package com.guavapay.delivery.repository;

import com.guavapay.delivery.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {

    Boolean existsByEmail(String email);

    Optional<UserData> findUserDataByEmail(String email);

}
