package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.GroceryItemEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryItemRepository;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.exception.NotFoundException;
import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryItemRequestModel;
import com.joliest.portfolios.groceryapi.model.GroceryRequestModel;
import com.joliest.portfolios.groceryapi.model.GroceryItem;
import com.joliest.portfolios.groceryapi.utils.GroceryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryService {
    private final GroceryRepository groceryRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final GroceryItemRepository groceryItemRepository;

    public Grocery addGrocery(GroceryRequestModel grocery) {
        StoreEntity storeEntity = storeRepository.findById(grocery.getStoreId())
                .orElseThrow(() -> new NotFoundException("Invalid store id"));
        GroceryEntity groceryEntity = GroceryEntity.builder()
                .name(grocery.getName())
                .description(grocery.getDescription())
                .store(storeEntity)
                .build();
        GroceryEntity savedGrocery = groceryRepository.save(groceryEntity);
        return GroceryUtil.convertEntityToGrocery(savedGrocery);
    }

    public List<Grocery> getGroceries() {
        List<GroceryEntity> fetchedGroceries = groceryRepository.findAll();

        return fetchedGroceries.stream()
                .map(GroceryUtil::convertEntityToGrocery)
                .toList();
    }

    public GroceryItem addGroceryItem(Integer groceryId, GroceryItemRequestModel requestBody) {
        GroceryEntity groceryEntity = groceryRepository.findById(groceryId)
                .orElseThrow(() -> new NotFoundException("Invalid grocery id"));
        ProductEntity productEntity = productRepository.findById(requestBody.getProductId())
                .orElseThrow(() -> new NotFoundException("Invalid product id"));

        GroceryItemEntity groceryItemEntity = GroceryItemEntity.builder()
                .product(productEntity)
                .grocery(groceryEntity)
                .quantity(requestBody.getQuantity())
                .notes(requestBody.getNotes())
                .actualPrice(requestBody.getActualPrice())
                .estimatedPrice(requestBody.getEstimatedPrice())
                .build();

        GroceryItemEntity savedGroceryItem = groceryItemRepository.save(groceryItemEntity);
        return GroceryUtil.convertEntityToGroceryItem(savedGroceryItem);
    }
}
