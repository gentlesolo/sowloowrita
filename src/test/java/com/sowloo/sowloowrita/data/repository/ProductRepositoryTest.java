//package com.sowloo.sowloowrita.data.repository;
//
//import com.sowloo.sowloowrita.data.models.Product;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@SpringBootTest
//@Sql(scripts = {"/db/insert.sql"})
//class ProductRepositoryTest {
//
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setUp() {
//    }
//
////    @AfterEach
////    void tearDown(){
////        productRepository.deleteAll();
////    }
//
//    @Test
//    @DisplayName("Save a new product to the Database")
//    void saveProductToDatabaseTest(){
//
//        Product product = new Product();
//
//        product.setName("World class Bamboo Chair");
//        product.setDescription("My Bamboo Chair is fine");
//        product.setPrice(5678);
//        product.setQuantity(8);
//
//        assertThat(product.getId()).isNull();
//
//        productRepository.save(product);
//        log.info("Product Saved -> {}", product);
//        assertThat(product.getId()).isNotNull();
//        assertThat(product.getName()).isEqualTo("World class Bamboo Chair");
//        assertThat(product.getPrice()).isEqualTo(5678);
//        assertThat(product.getQuantity()).isEqualTo(8);
//        assertThat(product.getDateCreated()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Find an existing product in the database")
//    void findExistingProductsInTheDatabase(){
//
//        Product product = productRepository.findById(12L).orElse(null);
//
//        assertThat(product).isNotNull();
//        assertThat(product.getId()).isEqualTo(12);
//        assertThat(product.getName()).isEqualTo("car");
//        assertThat(product.getPrice()).isEqualTo(50000);
//
//        log.info("Product retrieved -> {}", product);
//    }
//
//    @Test
//    @DisplayName("Find all product in the database")
//    void findAllProductsInTheDatabaseTest(){
//       List<Product> product = productRepository.findAll();
//        assertThat(product.size()).isEqualTo(4);
//        assertThat(product).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Find product by name")
//    void findThatProductExistInTheDatabase(){
//
//        Product productName = productRepository.findByName("television").orElse(null);
//
//        assertThat(productName).isNotNull();
//        assertThat(productName.getId()).isEqualTo(14);
//        assertThat(productName.getName()).isEqualTo("television");
//        assertThat(productName.getPrice()).isEqualTo(6000);
//
//    }
//
//    @Test
//    @DisplayName("Update product Test")
//    void checkThatProductCanBeUpdatedTest(){
//        // Given
//
//        Product savedProduct = productRepository.findByName("television").orElse(null);
//        assertThat(savedProduct).isNotNull();
//
//        assertThat(savedProduct.getName()).isEqualTo("television");
//        assertThat(savedProduct.getPrice()).isEqualTo(6000);
//
//        savedProduct.setName("chair");
//        savedProduct.setPrice(1500);
//
//        productRepository.save(savedProduct);
//        assertThat(savedProduct.getName()).isEqualTo("chair");
//        assertThat(savedProduct.getPrice()).isEqualTo(1500);
//
//    }
//}