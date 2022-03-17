//package com.sowloo.sowloowrita.service.cart;
//
//
//import com.sowloo.sowloowrita.data.dto.CartItemDto;
//import com.sowloo.sowloowrita.data.dto.CartResponseDto;
//import com.sowloo.sowloowrita.data.dto.CartUpdateDto;
//import com.sowloo.sowloowrita.data.models.*;
//import com.sowloo.sowloowrita.data.repository.AppUserRepository;
//import com.sowloo.sowloowrita.data.repository.CartRepository;
//import com.sowloo.sowloowrita.data.repository.ProductRepository;
//import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
//import com.sowloo.sowloowrita.web.exception.ProductDoesNotExistException;
//import com.sowloo.sowloowrita.web.exception.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.function.Predicate;
//
//@Service
//public class CartServiceImpl implements CartService{
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
//
//    @Override
//    public CartResponseDto addItemToCart(CartItemDto cartItemDto) {
//
//        Optional<AppUser> query = appUserRepository.findById(cartItemDto.getUserId());
//
//        if (query.isEmpty()){
//            throw new UserNotFoundException("User with id" + cartItemDto.getUserId()+ "not found");
//        }
//
//        AppUser existingUser = query.get();
//
//        Cart myCart = existingUser.getMyCart();
//
//        Product product =  productRepository.findById(cartItemDto.getProductId()).orElse(null);
//        if(product == null){
//            throw new ProductDoesNotExistException("Product with id" + cartItemDto.getProductId()+ "does not exist");
//        }
//
//        if (!quantityIsValid(product, cartItemDto.getQuantity())){
//            throw new BusinessLogicException("Quantity is too large");
//        }
//
//        Item cartItem = new Item(product, cartItemDto.getQuantity());
//
//        myCart.addItem(cartItem);
//
//        myCart.setTotalPrice(myCart.getTotalPrice() + calculateItemPrice(cartItem));
//
//        cartRepository.save(myCart);
//
//        return buildCartResponse(myCart);
//
//    }
//
//    private CartResponseDto buildCartResponse(Cart cart){
//        return CartResponseDto.builder()
//                .totalItemsInCart(cart.getItemList())
//                .totalPrice(cart.getTotalPrice())
//                .build();
//    }
//
//
//    private double calculateItemPrice(Item item){
//        return item.getProduct().getPrice() * item.getQuantityAddedCart();
//    }
//
//
//    private boolean quantityIsValid (Product product, int quantity){
//        return product.getQuantity() >= quantity;
//    }
//
//
//    @Override
//    public CartResponseDto viewCart(Long userId) {
//
//        AppUser appUser = appUserRepository.findById(userId).orElse(null);
//        if (appUser == null){
//            throw new BusinessLogicException("User not found");
//        }
//
//        Cart cart = appUser.getMyCart();
//
//
//        return buildCartResponse(cart);
//    }
//
//    @Override
//    public CartResponseDto updateCart(CartUpdateDto cartUpdateDto) {
//
//        AppUser appUser = appUserRepository.findById(cartUpdateDto.getUserId()).orElse(null);
//        if (appUser == null){
//            throw new BusinessLogicException("User with id " + cartUpdateDto.getUserId() + " does not exist");
//        }
//
//        Cart myCart = appUser.getMyCart();
//
//        Item item = findCartItem(cartUpdateDto.getItemId(), myCart).orElse(null);
//        if (item == null){
//            throw new BusinessLogicException("Item not in cart");
//        }
//
//        if (cartUpdateDto.getQuantityOp() == QuantityOp.INCREASE){
//            item.setQuantityAddedCart(item.getQuantityAddedCart() + 1);
//            myCart.setTotalPrice(myCart.getTotalPrice() + item.getProduct().getPrice());
//        }else
//            if (cartUpdateDto.getQuantityOp() == QuantityOp.DECREASE){
//                item.setQuantityAddedCart(item.getQuantityAddedCart() - 1);
//                myCart.setTotalPrice(myCart.getTotalPrice() - item.getProduct().getPrice());
//            }
//
//        cartRepository.save(myCart);
//
//        return buildCartResponse(myCart);
//    }
//
//    private Optional<Item> findCartItem(Long itemId, Cart cart){
//        Predicate<Item> itemPredicate = i -> i.getId().equals(itemId);
//        return cart.getItemList().stream().filter(itemPredicate).findFirst();
//    }
//
//
//}
