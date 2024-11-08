package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Store;
import com.joliest.portfolios.groceryapi.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/stores")
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Store> getStores() {
        return storeService.getStores();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Store> addMultipleStores(@RequestBody List<Store> stores) {
        return storeService.addMultipleStores(stores);
    }

}
