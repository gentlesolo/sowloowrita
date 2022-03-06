//package com.sowloo.sowloowrita.web.controllerTest;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sowloo.sowloowrita.data.dto.CartItemDto;
//import com.sowloo.sowloowrita.data.dto.CartResponseDto;
//import com.sowloo.sowloowrita.data.repository.CartRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//@Sql(scripts = {"/db/insert.sql"})
//public class CartControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("add a new item to cart")
//    void addItemToCartTest() throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        CartItemDto request = new CartItemDto();
//        request.setQuantity(2);
//        request.setUserId(5005L);
//        request.setProductId(12L);
//
//
//        mockMvc.perform(post("/api/cart")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().is(200))
//                .andDo(print());
//    }
//}
