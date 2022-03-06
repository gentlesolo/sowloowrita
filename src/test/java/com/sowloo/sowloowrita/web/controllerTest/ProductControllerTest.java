//package com.sowloo.sowloowrita.web.controllerTest;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sowloo.sowloowrita.data.models.Product;
//import com.sowloo.sowloowrita.data.repository.ProductRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Sql(scripts = {"/db/insert.sql"})
//class ProductControllerTest {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    private String createJsonObject;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() throws JsonProcessingException {
//
//        Product product = new Product();
//        product.setName("Tea Pot");
//        product.setPrice(8700);
//        product.setDescription("This is a delicate tea pot");
//        product.setQuantity(8);
//
//        createJsonObject = objectMapper.writeValueAsString(product);
//    }
//
////    @AfterEach
////    void tearDown() {
////        productRepository.deleteAll();
////    }
//
//    @Test
//    @DisplayName("Get Product api test")
//    void getProductTest() throws Exception {
//
//       mockMvc.perform(get("/api/v1/product")
//               .contentType("application/json"))
//               .andExpect(status().is(200))
//               .andDo(print());
//    }
//
//    @Test
//    @DisplayName("Post Product api test")
//    void postProductTest() throws Exception {
//
////        mockMvc.perform(post("/api/v1/product")
////                .contentType("application/json")
////                        .content(createJsonObject))
////                .andExpect(status().is(201))
////                .andDo(print());
//
//
//        MockHttpServletRequestBuilder request= MockMvcRequestBuilders.post("/api/v1/product")
//                .param("name", "Bamboo Chair")
//                .param("description", "World class bamboo")
//                .param("price", "5540")
//                .param("quantity", "5");
//
//        mockMvc.perform(request
//                        .contentType("multipart/form-data"))
//                .andExpect(status().is(201))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("Update product api test")
//    void updateProductTest() throws Exception {
//
//        Product product = productRepository.findById(14L).orElse(null);
//        assertThat(product).isNotNull();
//
//        mockMvc.perform(patch("/api/v1/product/14")
//                .contentType("application/json-patch+json")
//                .content(Files.readAllBytes(Path.of("payload.json"))))
//                .andExpect(status().is(200))
//                .andDo(print());
//
//
//        product = productRepository.findById(14L).orElse(null);
//        assertThat(product).isNotNull();
//        assertThat(product.getDescription()).isEqualTo("This is a good des");
//
//
//    }
//
//}