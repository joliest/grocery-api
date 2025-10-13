package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.ComponentEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ComponentProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ComponentProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ComponentRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.exception.NotFoundException;
import com.joliest.portfolios.groceryapi.model.ComponentModel;
import com.joliest.portfolios.groceryapi.utils.ComponentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentService {
    private final ComponentRepository componentRepository;
    private final ComponentProductRepository componentProductRepository;
    private final ProductRepository productRepository;

    public ComponentModel addComponent(ComponentModel requestModel) {
        ComponentEntity newComponent = ComponentEntity.builder()
                .name(requestModel.getName())
                .description(requestModel.getDescription())
                .build();
        ComponentEntity savedComponent = componentRepository.save(newComponent);
        return ComponentUtil.convertEntityToComponent(savedComponent);
    }

    // add rollback
    public ComponentModel editComponent(ComponentModel requestModel) {
        ComponentEntity component = componentRepository.findById(requestModel.getId())
                .orElseThrow(() -> new NotFoundException("Component not found"));

        List<ComponentProductEntity> componentList = requestModel.getList().stream()
                .map(item -> {
                    ProductEntity productEntity = productRepository.findById(item.getProduct().getId())
                            .orElseThrow(() -> new NotFoundException("Invalid product id"));
                    return ComponentProductEntity.builder()
                            .componentId(component.getId())
                            .product(productEntity)
                            .quantity(item.getQuantity())
                            .included(item.getIncluded())
                            .build();
                })
                .toList();
        List<ComponentProductEntity> componentProductEntityList =
                componentProductRepository.saveAll(componentList);
        component.setList(componentProductEntityList);
        return ComponentUtil.convertEntityToComponent(component);
    }

    public List<ComponentModel> getComponents() {
        return componentRepository.findAll().stream()
                .map(ComponentUtil::convertEntityToComponent)
                .toList();
    }

    public ComponentModel getComponent(Integer componentId) {
        ComponentEntity entity = componentRepository.findById(componentId)
                .orElseThrow(() -> new NotFoundException("Component not found"));
        return ComponentUtil.convertEntityToComponent(entity);
    }
}
