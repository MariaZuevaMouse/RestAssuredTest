package com.company.mz.restassured.repositories;

import com.company.mz.restassured.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
