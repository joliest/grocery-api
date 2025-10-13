package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.ComponentEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ComponentProductEntity;
import com.joliest.portfolios.groceryapi.model.ComponentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentUtil {

    public static ComponentModel convertEntityToComponent(ComponentEntity componentEntity) {
        List<ComponentModel.Item> items = Optional.ofNullable(componentEntity.getList()).orElseGet(ArrayList::new).stream()
                .map(ComponentUtil::convertEntityToComponentProduct)
                .toList();
        return ComponentModel.builder()
                .id(componentEntity.getId())
                .name(componentEntity.getName())
                .description(componentEntity.getDescription())
                .list(items)
                .build();
    }

    public static ComponentModel.Item convertEntityToComponentProduct(ComponentProductEntity componentProductEntity) {
        return ComponentModel.Item.builder()
                .id(componentProductEntity.getId())
                .product(ProductUtil.convertEntityToProduct(componentProductEntity.getProduct(), false))
                .quantity(componentProductEntity.getQuantity())
                .included(componentProductEntity.getIncluded())
                .build();
    }
}
