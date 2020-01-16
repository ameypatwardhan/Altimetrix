package com.altimetrix.altimetrixjpacrudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrix.altimetrixjpacrudexample.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
