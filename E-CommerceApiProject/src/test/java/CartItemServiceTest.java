import com.revature.models.CartItem;
import com.revature.repos.CartItemDAOImpl;
import com.revature.services.CartItemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class CartItemServiceTest {
    private CartItemService cartItemService;
    private CartItemDAOImpl mockDAO;

    @Before
    public void setup (){
        mockDAO = Mockito.mock(CartItemDAOImpl.class);
        cartItemService = new CartItemService(mockDAO);
    }

    @Test //If getAllCartItemsByUserId method works correct then return an cart items array list
    public void getAllCartItemsByUserIdShouldReturnCartItemsArrayList (){
        //Arrange
        int userId = 1;
        List<CartItem> allCartItems = new ArrayList<>();

        CartItem cartItem = new CartItem();

        allCartItems.add(cartItem);

        boolean result = false;

        when(mockDAO.getAllCartItemsByUserId(userId)).thenReturn(allCartItems);
        //Act
        if (!cartItemService.getAllCartItemsByUserId(userId).isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllByUserId method doesn't work correct then return an empty cart items array list
    public void getAllCartItemsByUserIdShouldReturnEmptyOrdersArrayList (){
        //Arrange
        int userId = 1;
        List<CartItem> allCartItems = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAllCartItemsByUserId(userId)).thenReturn(allCartItems);
        //Act
        if (cartItemService.getAllCartItemsByUserId(userId).isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If deleteCartItem method works correct then return true;
    public void deleteCartItemShouldReturnTrue (){
        //Arrange
        int itemId = 1;

        when(mockDAO.deleteById(itemId)).thenReturn(true);
        //Act
        boolean result = cartItemService.deleteCartItem(itemId);
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If deleteCartItem method doesn't work correct then return false;
    public void deleteCartItemShouldReturnFalse (){
        //Arrange
        int itemId = 1;

        when(mockDAO.deleteById(itemId)).thenReturn(false);
        //Act
        boolean result = cartItemService.deleteCartItem(itemId);
        //Assert
        Assert.assertFalse(result);
    }



}
