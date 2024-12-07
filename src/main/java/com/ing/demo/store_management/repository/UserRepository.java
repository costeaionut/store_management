package com.ing.demo.store_management.repository;

import com.ing.demo.store_management.model.authentication.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<StoreUser, Integer> {

}
