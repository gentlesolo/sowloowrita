//package com.sowloo.sowloowrita.service.cart;
//
//import com.sowloo.sowloowrita.data.dto.CartItemDto;
//import com.sowloo.sowloowrita.data.dto.CartResponseDto;
//import com.sowloo.sowloowrita.data.dto.CartUpdateDto;
//import com.sowloo.sowloowrita.data.models.*;
//import com.sowloo.sowloowrita.data.repository.CartRepository;
//import com.sowloo.sowloowrita.data.repository.ProductRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//@Slf4j
//@SpringBootTest
//@Sql("/db/insert.sql")
//class CartServiceImplTest {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private AppUserRepository appUserRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CartService cartService;
//
//    CartUpdateDto cartUpdateDto;
//
//    @BeforeEach
//    void setUp() {
//         cartUpdateDto = CartUpdateDto.builder()
//                .itemId(102L)
//                .quantityOp(QuantityOp.INCREASE)
//                .userId(5005L).build();
//    }
//
//
//
//
//    @Test
//    @DisplayName("add item to cart")
//    void addItemToCartTest(){
//
//        CartItemDto cartItemDto = new CartItemDto();
//
//        cartItemDto.setQuantity(1);
//        cartItemDto.setUserId(5005L);
//        cartItemDto.setProductId(12L);
//
//        AppUser userInDb = appUserRepository.findById(cartItemDto.getUserId()).orElse(null);
//        assertThat(userInDb).isNotNull();
//
//        Cart myCart = userInDb.getMyCart();
//        assertThat(myCart).isNotNull();
//
//        Product product = productRepository.findById(12L).orElse(null);
//        assertThat(product).isNotNull();
//        assertThat(product.getQuantity()).isGreaterThanOrEqualTo(cartItemDto.getQuantity());
//
//
//        Item cartItem = new Item(product, cartItemDto.getQuantity());
//
//        myCart.addItem(cartItem);
//
//        cartRepository.save(myCart);
//        assertThat(myCart.getItemList().size()).isEqualTo(4);
//
//    }
//
//
//    @Test
//    @DisplayName("Add item to cart test")
//    void addItemToCartTest2(){
//
//        CartItemDto cartItemDto = new CartItemDto();
//
//        cartItemDto.setQuantity(1);
//        cartItemDto.setUserId(5005L);
//        cartItemDto.setProductId(12L);
//
//        CartResponseDto cartResponseDto = cartService.addItemToCart(cartItemDto);
//        assertThat(cartResponseDto.getTotalItemsInCart()).isNotNull();
//        assertThat(cartResponseDto.getTotalPrice()).isNotNull();
//    }
//
//
//    @Test
//    @DisplayName("Cart price updated test")
//    void cartPriceUpdatedTest(){
//        CartItemDto cartItemDto = new CartItemDto();
//
//        cartItemDto.setQuantity(2);
//        cartItemDto.setUserId(5005L);
//        cartItemDto.setProductId(12L);
//
//        CartResponseDto cartResponseDto = cartService.addItemToCart(cartItemDto);
//        assertThat(cartResponseDto.getTotalPrice()).isEqualTo(1000);
//        assertThat(cartResponseDto.getTotalItemsInCart().size()).isEqualTo(4);
//    }
//
//    @Test
//    @DisplayName("update cart quantity item test")
//    void updateCartItemTest(){
//
//        AppUser appUser = appUserRepository.findById(cartUpdateDto.getUserId()).orElse(null);
//        assertThat(appUser).isNotNull();
//
//        Cart userCart = appUser.getMyCart();
//
//        assertThat(userCart.getItemList().size()).isEqualTo(1);
//
//        Item item = userCart.getItemList().get(0);
//
////        Predicate<Item> itemPredicate  = item1 -> item1.getId().equals(cartUpdateDto.getItemId());
////
////        Optional<Item> optionalItem = userCart.getItemList().stream().filter(itemPredicate).findFirst();
//
//
////        item = optionalItem.get();
////
////         for(int i = 0; i < userCart.getItemList().size() ; i++){
////            item = userCart.getItemList().get(i);
////            if(Objects.equals(item.getId(), cartUpdateDto.getItemId())){
////
////                break;
////            }
////        }
//
//        log.info("item -> {}", item);
//        assertThat(item).isNotNull();
//         assertThat(item.getQuantityAddedCart()).isEqualTo(2);
//        log.info("Cart Update obj -> {}", cartUpdateDto);
//
//       CartResponseDto responseDto = cartService.updateCart(cartUpdateDto);
//       assertThat(responseDto.getTotalItemsInCart()).isNotNull();
//       assertThat(responseDto.getTotalItemsInCart().get(0).getQuantityAddedCart()).isEqualTo(3);
//
////
////        for(int i = 0; i < userCart.getItemList().size() ; i++){
////            item = responseDto.getTotalItemsInCart().get(i);
////            if(Objects.equals(item.getId(), cartUpdateDto.getItemId())){
////
////                break;
////            }
////        }
////        assertThat(item).isNotNull();
//    }
//
//}