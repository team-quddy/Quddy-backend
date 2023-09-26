package com.team_quddy.quddy.user.repository;

import com.team_quddy.quddy.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users getUsersById(Integer id);
}
