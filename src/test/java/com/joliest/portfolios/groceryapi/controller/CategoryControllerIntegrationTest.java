package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryControllerIntegrationTest {
    static String CATERGORY_URI = "/v1/categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Description("Get All Categories")
    public void getCategories() {
        List<Integer> categoryIds = setupCategories();
        webTestClient
                .get()
                .uri(CATERGORY_URI)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Category.class);
        cleanup(categoryIds);
    }

    @Test
    @Description("Create Categories")
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
                    cleanup(categoryIds);
                });
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


    private void cleanup(List<Integer> ids) {
        categoryRepository.deleteAllById(ids);
    }
}