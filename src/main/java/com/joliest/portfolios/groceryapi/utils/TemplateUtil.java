package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.TemplateEntity;
import com.joliest.portfolios.groceryapi.domain.entity.TemplateProductEntity;
import com.joliest.portfolios.groceryapi.model.TemplateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TemplateUtil {

    public static TemplateModel convertEntityToTemplate(TemplateEntity templateId) {
        List<TemplateModel.Item> items = Optional.ofNullable(templateId.getList()).orElseGet(ArrayList::new).stream()
                .map(TemplateUtil::convertEntityToTemplateProduct)
                .toList();
        return TemplateModel.builder()
                .id(templateId.getId())
                .name(templateId.getName())
                .description(templateId.getDescription())
                .list(items)
                .build();
    }

    public static TemplateModel.Item convertEntityToTemplateProduct(TemplateProductEntity templateProductEntity) {
        return TemplateModel.Item.builder()
                .id(templateProductEntity.getId())
                .product(ProductUtil.convertEntityToProduct(templateProductEntity.getProduct(), false))
                .quantity(templateProductEntity.getQuantity())
                .included(templateProductEntity.getIncluded())
                .build();
    }
}
