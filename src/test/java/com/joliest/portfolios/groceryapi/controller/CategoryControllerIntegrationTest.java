package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerIntegrationTest extends BaseIntegrationTest {
    static String CATERGORY_URI = "/v1/categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    @DisplayName("Create Categories")
    @Description("Scenario: Happy Path" +
            "Given POST v1/categories and valid request body is provided, " +
            "When POST Endpoint is called, " +
            "Then it will send the new category as a response")
    public void postCategories() {
        List<Category> requestBody = createRequestBody();
        ResponseEntity<List<Category>> response = restTemplate.exchange(CATERGORY_URI, HttpMethod.POST, new HttpEntity<>(requestBody), new ParameterizedTypeReference<>() {});
        List<Category> stores = response.getBody();

        assert stores != null;
        assertThat(stores.get(0).getName()).isNotNull();
        assertThat(stores.get(0).getDescription()).isNotNull();

        assertThat(stores.get(1).getName()).isNotNull();
        assertThat(stores.get(1).getDescription()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("Get All Categories")
    @Description("Scenario: Happy Path" +
            "Given GET v1/categories is the endpoint" +
            "When GET endpoint is called" +
            "Then it will send the list of categories")
    public void getCategories() {
        ParameterizedTypeReference<List<Category>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Category>> response = restTemplate.exchange(CATERGORY_URI, HttpMethod.GET, null, responseType);
        List<Category> stores = response.getBody();

        assert stores != null;
        assertThat(stores.get(0).getName()).isNotNull();
        assertThat(stores.get(0).getDescription()).isNotNull();

        assertThat(stores.get(1).getName()).isNotNull();
        assertThat(stores.get(1).getDescription()).isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("Delete Categories")
    @Description("Scenario: Happy Path" +
            "Given DELETE v1/categories/:categoryId" +
            "When DELETE Endpoint is called" +
            "Then category id should be deleted")
    public void deleteCategory() {
        ResponseEntity<Void> response = restTemplate.exchange(CATERGORY_URI + "/" + 1, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(4)
    @DisplayName("Delete Categories: Cascade")
    @Description("Scenario: Subcategories are available" +
            "When Category is deleted" +
            "Then it's Subcategories will also be deleted")
    public void deleteCategoryCascade() {
        // given
        Integer categoryId = setupCategories().get(0);
        Integer subcategoryId = setupSubcategories(categoryId).get(0);

        // when
        ResponseEntity<Void> response = restTemplate.exchange(CATERGORY_URI + "/" + categoryId, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        // then
        Optional<SubcategoryEntity> deletedSubcategory = subcategoryRepository.findById(subcategoryId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deletedSubcategory.isPresent()).isFalse();
    }

    private List<Integer> setupSubcategories(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(categoryEntity)
                .name("Subcategory 1")
                .description("Desc 1")
                .build();
        List<SubcategoryEntity> subcategoriesToSave = asList(subcategory);
        List<SubcategoryEntity> savedSubcategory = subcategoryRepository.saveAll(subcategoriesToSave);
        return savedSubcategory.stream()
                .map(SubcategoryEntity::getId)
                .collect(Collectors.toList());
    }


    private List<Integer> setupCategories() {
        CategoryEntity category1 = CategoryEntity.builder()
                .name("Category 1111")
                .description("Desc 111")
                .build();
        CategoryEntity category2 = CategoryEntity.builder()
                .name("Category 2222")
                .description("Desc 2222")
                .build();
        List<CategoryEntity> categoriesToSave = asList(category1, category2);
        List<CategoryEntity> savedCategory = categoryRepository.saveAll(categoriesToSave);
        return savedCategory.stream()
                .map(CategoryEntity::getId)
                .collect(Collectors.toList());
    }

    private List<Category> createRequestBody() {
        Category category1 = Category.builder()
                .name("Category 1")
                .description("Desc 1")
                .build();
        Category category2 = Category.builder()
                .name("Category 2")
                .description("Desc 2")
                .build();
        return asList(category1, category2);
    }
}