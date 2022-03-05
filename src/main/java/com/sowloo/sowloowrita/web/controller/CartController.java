//package com.sowloo.sowloowrita.web.controller;
//
//
//import com.sowloo.sowloowrita.data.dto.CartItemDto;
//import com.sowloo.sowloowrita.data.dto.CartResponseDto;
//import com.sowloo.sowloowrita.data.dto.CartUpdateDto;
//import com.sowloo.sowloowrita.service.cart.CartService;
//import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
//import com.sowloo.sowloowrita.web.exception.ProductDoesNotExistException;
//import com.sowloo.sowloowrita.web.exception.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//
//    @PostMapping("")
//    public ResponseEntity<?>addItemToCart(@RequestBody CartItemDto cartItemDto){
//        CartResponseDto cartResponseDto = null;
//        try{
//            cartResponseDto = cartService.addItemToCart(cartItemDto);
//        }catch (ProductDoesNotExistException | UserNotFoundException | BusinessLogicException e){
//            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//        return ResponseEntity.ok().body(cartResponseDto);
//    }
//
//    @PatchMapping("")
//    public ResponseEntity<?>updateCartItem(@RequestBody CartUpdateDto cartUpdateDto){
//
//        try {
//            return new ResponseEntity<>(cartService.updateCart(cartUpdateDto), HttpStatus.OK);
//        }catch (BusinessLogicException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<?>viewCart(@PathVariable Long userId){
//        try{
//            CartResponseDto responseDto = cartService.viewCart(userId);
//            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//        }catch (BusinessLogicException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//}
