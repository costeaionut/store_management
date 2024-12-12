package com.ing.demo.store_management.repository;

import com.ing.demo.store_management.model.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer> {
}
