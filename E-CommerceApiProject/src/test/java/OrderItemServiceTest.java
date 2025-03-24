import com.revature.models.OrderItem;
import com.revature.repos.OrderItemDAOImpl;
import com.revature.services.OrderItemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class OrderItemServiceTest {
    private OrderItemService orderItemService;
    private OrderItemDAOImpl mockDAO;

    @Before
    public void setup(){
        mockDAO = Mockito.mock(OrderItemDAOImpl.class);

        orderItemService = new OrderItemService(mockDAO);
    }

    @Test // If createOrderItem works correct then return the created order item
    public void createOrderItemShouldReturnCreatedOrderItem (){
        //Arrange
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder_id(1);
        orderItem.setJersey_id(1);
        orderItem.setQuantity(1);
        orderItem.setPrice(90.0);

        when(mockDAO.create(orderItem)).thenReturn(orderItem);
        //Act
        OrderItem createdOrder = orderItemService.createOrderItem(1,1,1,90.0);
        //Assert
        Assert.assertEquals(orderItem, createdOrder);
    }
}
