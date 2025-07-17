package com.truemed.Abs.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truemed.Abs.Entity.CustomUser;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

	Optional<CustomUser> findByUsernameOrEmail(String username,String email);
	Optional<CustomUser> findByEmail(String email);
	Optional<CustomUser> findByMobile(String mobile);
	
}
