package com.company.mz.restassured.services;

import com.company.mz.restassured.entities.Item;
import com.company.mz.restassured.exceptions.ResourceNotFoundException;
import com.company.mz.restassured.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findOneById(Long id){
        return itemRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Item [id = " + id + "] not found"));
    }

    public  Item createOrUpdateItem(Item item){
        return itemRepository.save(item);
    }

    public boolean isItmExistById(Long id){
        return itemRepository.existsById(id);
    }
}
