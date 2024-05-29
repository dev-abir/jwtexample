package org.devabir.jwtexample.repository;

import org.devabir.jwtexample.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao, String> {
}
