package com.cydeo.lab04springmvc.service.impl;

import com.cydeo.lab04springmvc.model.Cart;
import com.cydeo.lab04springmvc.model.CartItem;
import com.cydeo.lab04springmvc.service.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cydeo.lab04springmvc.service.impl.ProductServiceImpl.PRODUCT_LIST;

@Service
public class CartServiceImpl implements CartService {
    public static List<Cart> CART_LIST = new ArrayList<>();
    @Override
    public List<Cart> retrieveCartList() {
        return CART_LIST;
    }
    @Override
    public List<CartItem> retrieveCartDetail(UUID cartId) {
        return CART_LIST.stream()
                .filter(i -> i.getId().equals(cartId))
                .map(Cart::getCartItemList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // todo implement method using stream
    }

    @Override
    public void initialiseCartList() {
        Cart cart1 = new Cart();
        cart1.setId(UUID.randomUUID());
        List<CartItem> cartItemList = new ArrayList<>();

        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(PRODUCT_LIST.get(0).getQuantity());
        cartItem1.setProduct(PRODUCT_LIST.get(0));

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(PRODUCT_LIST.get(2).getQuantity());
        cartItem2.setProduct(PRODUCT_LIST.get(2));
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);

        List<CartItem> cartItemList1 = new ArrayList<>();

        CartItem cartItem3 = new CartItem();
        cartItem3.setQuantity(PRODUCT_LIST.get(1).getQuantity());
        cartItem3.setProduct(PRODUCT_LIST.get(1));

        CartItem cartItem4 = new CartItem();
        cartItem4.setQuantity(PRODUCT_LIST.get(3).getQuantity());
        cartItem4.setProduct(PRODUCT_LIST.get(3));
        cartItemList1.add(cartItem3);
        cartItemList1.add(cartItem4);
        cartItemList1.add(cartItem2);


        cart1.setCartItemList(cartItemList);
        BigDecimal cart1TotalAmount = BigDecimal.ZERO;
        BigDecimal cart2TotalAmount = BigDecimal.ZERO;

        // todo change to stream
        cart1TotalAmount = cartItemList.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .reduce(BigDecimal::add).get();
//        for (CartItem cartItem : cartItemList){
//            cart1TotalAmount = cart1TotalAmount.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
//        }

        CART_LIST.add(cart1);
        cart1.setCartTotalAmount(cart1TotalAmount);
        Cart cart2 = new Cart();
        cart2.setId(UUID.randomUUID());
        cart2.setCartItemList(cartItemList1);

        // todo change to stream
        cart2TotalAmount = cartItemList1.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal::add).get();
//        for (CartItem cartItem : cartItemList1){
//            cart2TotalAmount = cart1TotalAmount.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
//        }

        cart2.setCartTotalAmount(cart2TotalAmount);
        CART_LIST.add(cart2);

    }
}
