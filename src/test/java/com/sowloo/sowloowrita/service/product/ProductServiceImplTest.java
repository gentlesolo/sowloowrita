package com.sowloo.sowloowrita.service.product;

import com.sowloo.sowloowrita.data.models.Product;
import com.sowloo.sowloowrita.data.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void applyPatchToProductTest(){

        Product product = new Product();
        product.setName("Tea Pot");
        product.setPrice(8700);
        product.setDescription("This is a delicate tea pot");
        product.setQuantity(8);

        productRepository.save(product);



    }
}