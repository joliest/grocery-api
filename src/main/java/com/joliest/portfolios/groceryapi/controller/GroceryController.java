package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.service.GroceryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/groceries")
public class GroceryController {

    private final GroceryService groceryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Grocery addGrocery(@RequestBody Grocery grocery) {
        return groceryService.addGrocery(grocery);
    }

    @GetMapping
    public List<Grocery> getGroceries() {
        return groceryService.getGroceries();
    }

    @PostMapping("/{groceryId}/item/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Grocery addGroceryItem(@RequestBody Grocery grocery,
                                  @PathVariable Integer groceryId,
                                  @PathVariable Integer productId
    ) {
        return groceryService.addGrocery(grocery);
    }
}
