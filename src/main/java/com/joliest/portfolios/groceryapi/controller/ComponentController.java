package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.ComponentModel;
import com.joliest.portfolios.groceryapi.service.ComponentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/components")
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentModel addComponent(@RequestBody ComponentModel requestModel) {
        return componentService.addComponent(requestModel);
    }

    @PutMapping("/{componentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ComponentModel editComponent(@PathVariable Integer componentId,
                                        @RequestBody ComponentModel requestModel) {
        requestModel.setId(componentId);
        return componentService.editComponent(requestModel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ComponentModel> getComponent() {
        return componentService.getComponents();
    }

    @GetMapping("/{componentId}")
    @ResponseStatus(HttpStatus.OK)
    public ComponentModel getComponent(@PathVariable Integer componentId) {
        return componentService.getComponent(componentId);
    }

}
