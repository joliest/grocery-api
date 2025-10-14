package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.TemplateEntity;
import com.joliest.portfolios.groceryapi.domain.entity.TemplateProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.repository.TemplateProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.TemplateRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.exception.NotFoundException;
import com.joliest.portfolios.groceryapi.model.TemplateModel;
import com.joliest.portfolios.groceryapi.utils.TemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateProductRepository templateProductRepository;
    private final ProductRepository productRepository;

    public TemplateModel addTemplate(TemplateModel requestModel) {
        TemplateEntity newTemplate = TemplateEntity.builder()
                .name(requestModel.getName())
                .description(requestModel.getDescription())
                .build();
        TemplateEntity savedTemplate = templateRepository.save(newTemplate);
        return TemplateUtil.convertEntityToTemplate(savedTemplate);
    }

    // add rollback
    public TemplateModel editTemplate(TemplateModel requestModel) {
        TemplateEntity template = templateRepository.findById(requestModel.getId())
                .orElseThrow(() -> new NotFoundException("Template not found"));

        List<TemplateProductEntity> templateList = requestModel.getList().stream()
                .map(item -> {
                    ProductEntity productEntity = productRepository.findById(item.getProduct().getId())
                            .orElseThrow(() -> new NotFoundException("Invalid product id"));
                    return TemplateProductEntity.builder()
                            .templateId(template.getId())
                            .product(productEntity)
                            .quantity(item.getQuantity())
                            .included(item.getIncluded())
                            .build();
                })
                .toList();
        List<TemplateProductEntity> templateProductEntityList =
                templateProductRepository.saveAll(templateList);
        template.setList(templateProductEntityList);
        return TemplateUtil.convertEntityToTemplate(template);
    }

    public List<TemplateModel> getTemplates() {
        return templateRepository.findAll().stream()
                .map(TemplateUtil::convertEntityToTemplate)
                .toList();
    }

    public TemplateModel getTemplate(Integer templateId) {
        TemplateEntity entity = templateRepository.findById(templateId)
                .orElseThrow(() -> new NotFoundException("Template not found"));
        return TemplateUtil.convertEntityToTemplate(entity);
    }
}
