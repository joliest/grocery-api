package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.TemplateModel;
import com.joliest.portfolios.groceryapi.service.TemplateService;
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
@RequestMapping("/v1/templates")
public class TemplatesController {

    private final TemplateService templateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateModel addTemplate(@RequestBody TemplateModel requestModel) {
        return templateService.addTemplate(requestModel);
    }

    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TemplateModel editTemplate(@PathVariable Integer templateId,
                                        @RequestBody TemplateModel requestModel) {
        requestModel.setId(templateId);
        return templateService.editTemplate(requestModel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateModel> getTemplate() {
        return templateService.getTemplates();
    }

    @GetMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateModel getTemplate(@PathVariable Integer templateId) {
        return templateService.getTemplate(templateId);
    }

}
