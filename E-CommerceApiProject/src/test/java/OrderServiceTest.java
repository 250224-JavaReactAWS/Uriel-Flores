import com.revature.models.Order;
import com.revature.repos.OrderDAOImpl;
import com.revature.services.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private OrderService orderService;
    private OrderDAOImpl mockDAO;

    @Before
    public  void setup(){
        mockDAO = Mockito.mock(OrderDAOImpl.class);

        orderService = new OrderService(mockDAO);
    }

    @Test //If getOrdersById method works correct then return an Order
    public void getOrderByIdShouldReturnOrder (){
        //Arrange
        int orderId = 1;
        Order o = new Order();
        o.setOrder_id(1);

        when(mockDAO.getById(orderId)).thenReturn(o);
        //Act
        Order order = orderService.getOrderById(orderId);
        //Assert
        Assert.assertNotNull(order);
    }

    @Test //If getOrdersById method doesn't work correct then return null
    public void getOrderByIdShouldReturnNull (){
        //Arrange
        int orderId = 1;

        when(mockDAO.getById(orderId)).thenReturn(null);
        //Act
        Order order = orderService.getOrderById(orderId);
        //Assert
        Assert.assertNull(order);
    }

    @Test //If getAllByUserId method works correct then return an orders array list
    public void getAllByUserIdShouldReturnOrdersArrayList (){
        //Arrange
        int userId = 1;
        List<Order> allOrders = new ArrayList<>();

        Order o = new Order();

        allOrders.add(o);

        boolean result = false;

        when(mockDAO.getAllByUserId(userId)).thenReturn(allOrders);
        //Act
        if (!orderService.getAllByUserId(userId).isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllByUserId method doesn't work correct then return an empty orders array list
    public void getAllByUserIdShouldReturnEmptyOrdersArrayList (){
        //Arrange
        int userId = 1;
        List<Order> allOrders = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAllByUserId(userId)).thenReturn(allOrders);
        //Act
        if (orderService.getAllByUserId(userId).isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAll method works correct then return an orders array list
    public void getAllShouldReturnOrdersArrayList (){
        //Arrange
        List<Order> allOrders = new ArrayList<>();

        Order o = new Order();

        allOrders.add(o);

        boolean result = false;

        when(mockDAO.getAll()).thenReturn(allOrders);
        //Act
        if (!orderService.getAll().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAll method doesn't work correct then return an empty orders array list
    public void getAllShouldReturnEmptyOrdersArrayList (){
        //Arrange

        List<Order> allOrders = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAll()).thenReturn(allOrders);
        //Act
        if (orderService.getAll().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllPendingOrders method works correct then return an orders array list
    public void getAllPendingOrdersShouldReturnOrdersArrayList (){
        //Arrange
        List<Order> allOrders = new ArrayList<>();

        Order o = new Order();

        allOrders.add(o);

        boolean result = false;

        when(mockDAO.getAllPendingOrders()).thenReturn(allOrders);
        //Act
        if (!orderService.getAllPendingOrders().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllPendingOrders method doesn't work correct then return an empty orders array list
    public void getAllPendingOrdersShouldReturnEmptyOrdersArrayList (){
        //Arrange

        List<Order> allOrders = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAllPendingOrders()).thenReturn(allOrders);
        //Act
        if (orderService.getAllPendingOrders().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllShippedOrders method works correct then return an orders array list
    public void getAllShippedOrdersShouldReturnOrdersArrayList (){
        //Arrange
        List<Order> allOrders = new ArrayList<>();

        Order o = new Order();

        allOrders.add(o);

        boolean result = false;

        when(mockDAO.getAllShippedOrders()).thenReturn(allOrders);
        //Act
        if (!orderService.getAllShippedOrders().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllShippedOrders method doesn't work correct then return an empty orders array list
    public void getAllShippedOrdersShouldReturnEmptyOrdersArrayList (){
        //Arrange

        List<Order> allOrders = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAllShippedOrders()).thenReturn(allOrders);
        //Act
        if (orderService.getAllShippedOrders().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllDelivered Orders method works correct then return an orders array list
    public void getAllDeliveredOrdersShouldReturnOrdersArrayList (){
        //Arrange
        List<Order> allOrders = new ArrayList<>();

        Order o = new Order();

        allOrders.add(o);

        boolean result = false;

        when(mockDAO.getAllDeliveredOrders()).thenReturn(allOrders);
        //Act
        if (!orderService.getAllDeliveredOrders().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If getAllDeliveredOrders method doesn't work correct then return an empty orders array list
    public void getAllDeliveredOrdersShouldReturnEmptyOrdersArrayList (){
        //Arrange

        List<Order> allOrders = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAllDeliveredOrders()).thenReturn(allOrders);
        //Act
        if (orderService.getAllDeliveredOrders().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If updateStatus method works correct then return an updated Order
    public void updateStatusShouldReturnUpdatedOrder (){
        //Arrange
        int orderId = 1;
        int statusId = 2;

        Order o = new Order();
        o.setOrder_id(1);
        o.setStatus(2);

        when(mockDAO.updateStatusOrder(orderId,statusId)).thenReturn(o);
        //Act
        Order updatedOrder = orderService.updateStatus(orderId,statusId);
        //Assert
        Assert.assertEquals(o,updatedOrder);
    }




}
