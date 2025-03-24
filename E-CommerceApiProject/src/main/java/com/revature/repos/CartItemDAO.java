package com.revature.repos;

import com.revature.models.CartItem;

import java.util.List;

public interface CartItemDAO extends GeneralDAO<CartItem>{
    List<CartItem> getAllCartItemsByUserId(int id);

    double getCartTotalByUserId(int id);
    double getTotalPerItem (int id);
    boolean deleteAllItems (int user_id);

}
