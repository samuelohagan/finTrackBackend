package com.samproject.financeapp.repository;

import com.samproject.financeapp.model.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EntityRepository extends CrudRepository<MyEntity, Long> {
}
