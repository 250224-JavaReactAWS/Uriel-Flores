package com.revature.services;

import com.revature.models.CartItem;
import com.revature.repos.CartItemDAO;

import java.util.List;

public class CartItemService {

    private CartItemDAO cartItemDAO;

    public CartItemService(CartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }
    //Method to add a Cart Item
    public CartItem addCartItem (CartItem cartItem){
        return cartItemDAO.create(cartItem);
    }

    //Method to get all cart items that belong a specific user
    public List<CartItem> getAllCartItemsByUserId (int id){
        return cartItemDAO.getAllCartItemsByUserId(id);
    }

    //Method to delete a specific Item from the Cart
    public boolean deleteCartItem (int itemID){
        return cartItemDAO.deleteById(itemID);
    }

    //Method to get CartItem by Id
    public CartItem getCartItemById (int id){
        return cartItemDAO.getById(id);
    }

    //Method to get the Total price of the products
    public Double getCartTotal (int userId){
        return cartItemDAO.getCartTotalByUserId(userId);
    }

//    public Double getTotalPerItem (int userId){
//        return cartItemDAO.getTotalPerItem(userId);
//    }

    public boolean deleteAllItems (int userId){
        return cartItemDAO.deleteAllItems(userId);
    }
}
