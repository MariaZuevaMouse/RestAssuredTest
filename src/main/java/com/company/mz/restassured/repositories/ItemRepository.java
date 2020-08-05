package com.company.mz.restassured.repositories;

import com.company.mz.restassured.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
