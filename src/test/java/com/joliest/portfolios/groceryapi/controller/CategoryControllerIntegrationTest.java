package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryControllerIntegrationTest {
    static String CATERGORY_URI = "/v1/categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Get All Categories")
    @Description("Scenario: Happy Path" +
            "Given GET v1/categories is the endpoint" +
            "When GET endpoint is called" +
            "Then it will send the list of categories")
    public void getCategories() {
        List<Integer> categoryIds = setupCategories();
        webTestClient
                .get()
                .uri(CATERGORY_URI)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Category.class);
        cleanupCategory(categoryIds);
    }

    @Test
    @DisplayName("Create Categories")
    @Description("Scenario: Happy Path" +
            "Given POST v1/categories and valid request body is provided" +
            "When POST Endpoint is called" +
            "Then it will send the new category as a response")
    public void postCategories() {
        List<Category> requestBody = createRequestBody();
        webTestClient
                .post()
                .uri(CATERGORY_URI)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBodyList(Category.class)
                //cleanup
                .consumeWith(result -> {
                    List<Integer> categoryIds = result.getResponseBody()
                            .stream()
                            .map(Category::getId)
                            .collect(Collectors.toList());
                    cleanupCategory(categoryIds);
                });
    }

    @Test
    @DisplayName("Delete Categories")
    @Description("Scenario: Happy Path" +
            "Given DELETE v1/categories/:categoryId" +
            "When DELETE Endpoint is called" +
            "Then category id should be deleted")
    public void deleteCategory() {
        List<Integer> categoryIds = setupCategories();
        Integer categoryId = categoryIds.get(0);
        webTestClient
                .delete()
                .uri(CATERGORY_URI + "/" + categoryId)
                .exchange()
                .expectStatus()
                .isOk();
        Optional<CategoryEntity> deletedCategory = categoryRepository.findById(categoryId);
        assertThat(deletedCategory.isPresent()).isFalse();
        cleanupCategory(categoryIds);
    }

    @Test
    @DisplayName("Delete Categories: Cascade")
    @Description("Scenario: Subcategories are available" +
            "When Category is deleted" +
            "Then it's Subcategories will also be deleted")
    public void deleteCategoryCascade() {
        List<Integer> categoryIds = setupCategories();
        Integer categoryId = categoryIds.get(0);
        List<Integer> subcategoryIds = setupSubcategories(categoryId);
        webTestClient
                .delete()
                .uri(CATERGORY_URI + "/" + categoryId)
                .exchange()
                .expectStatus()
                .isOk();
        Integer subcategoryId = subcategoryIds.get(0);
        Optional<SubcategoryEntity> deletedSubcategory = subcategoryRepository.findById(subcategoryId);
        assertThat(deletedSubcategory.isPresent()).isFalse();

        cleanupCategory(categoryIds);
        cleanupSubcategory(subcategoryIds);
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
                .name("Category 1")
                .description("Desc 1")
                .build();
        CategoryEntity category2 = CategoryEntity.builder()
                .name("Category 2")
                .description("Desc 2")
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


    private void cleanupCategory(List<Integer> ids) {
        categoryRepository.deleteAllById(ids);
    }

    private void cleanupSubcategory(List<Integer> ids) {
        subcategoryRepository.deleteAllById(ids);
    }
}