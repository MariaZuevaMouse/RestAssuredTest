package com.company.mz.restassured.services;

import com.company.mz.restassured.entities.Client;
import com.company.mz.restassured.exceptions.ClientNotFoundException;
import com.company.mz.restassured.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findOneById(Long id){
        return clientRepository.findById(id).orElseThrow(()-> new ClientNotFoundException("Client [id = " + id + "] not found"));
    }
    public Client createClient(Client client){
        return clientRepository.save(client);
    }
}
