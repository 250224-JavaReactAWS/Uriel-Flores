import com.revature.models.Jersey;
import com.revature.repos.JerseyDAOImpl;
import com.revature.services.JerseyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class JerseyServiceTest {

    private JerseyService jerseyService;
    private JerseyDAOImpl mockDAO;

    @Before
    public void setup(){
        mockDAO = Mockito.mock(JerseyDAOImpl.class);

        jerseyService = new JerseyService(mockDAO);
    }

    @Test // If getJerseysByTeamName method works correct then return a Jersey ArrayList
    public void getByTeamNameShouldReturnJerseysArrayList (){
        //Arrange
        List<Jersey> allJerseys = new ArrayList<>();

        String teamName = "Real Madrid";
        Jersey j = new Jersey();
        allJerseys.add(j);

        boolean result = false;

        when(mockDAO.getByTeamName(teamName)).thenReturn(allJerseys);
        //Act
        if (!jerseyService.getByTeamName(teamName).isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);
    }

    @Test // If getJerseysByTeamName method doesn't work correct then return an empty Jersey ArrayList
    public void getByTeamNameShouldReturnEmptyJerseysArrayList (){
        //Arrange
        List<Jersey> allJerseys = new ArrayList<>();

        String teamName = "Real Madrid";

        boolean result = false;

        when(mockDAO.getByTeamName(teamName)).thenReturn(allJerseys);
        //Act
        if (!jerseyService.getByTeamName(teamName).isEmpty()){
            result = false;
        }
        //Assert
        Assert.assertFalse(result);
    }

    @Test //If getById method works correct then return a jersey
    public void getByIdShouldReturnUser (){
        //Arrange
        Jersey j = new Jersey();
        int id = 1;
        j.setJersey_id(id);

        when(mockDAO.getById(id)).thenReturn(j);

        //Act
        Jersey jersey = jerseyService.getById(id);

        //Assert
        Assert.assertNotNull(jersey);

    }

    @Test //If getById method doesn't work correct then return null
    public void getByIdShouldReturnNull (){
        //Arrange
        int id = 1;

        when(mockDAO.getById(id)).thenReturn(null);

        //Act
        Jersey jersey = jerseyService.getById(id);

        //Assert
        Assert.assertNull(jersey);

    }

    @Test //If getAll method works correct then return a Jerseys ArrayList
    public void getAllShouldReturnJerseysArrayList (){
        //Arrange
        List<Jersey> allJerseys = new ArrayList<>();

        Jersey j = new Jersey();
        allJerseys.add(j);

        boolean result = false;

        when(mockDAO.getAll()).thenReturn(allJerseys);
        //Act
        if (!jerseyService.getAllJerseys().isEmpty()){
            result = true;
        }
        //Assert
        Assert.assertTrue(result);

    }

    @Test //If getAll method doesn't work correct then return an empty Jerseys ArrayList
    public void getAllShouldReturnEmptyJerseysArrayList (){
        //Arrange
        List<Jersey> allJerseys = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAll()).thenReturn(allJerseys);
        //Act
        if (!jerseyService.getAllJerseys().isEmpty()){
            result = false;
        }
        //Assert
        Assert.assertNotNull(result);
    }

    @Test //If addNewJersey method works correct then return the added Jersey
    public  void addNewJerseyShouldReturnAddedJersey (){
        //Arrange
        String size = "S";
        double price = 100.50;
        int stock = 40;
        int team_id = 1;
        int jersey_type_id = 1;

        Jersey j = new Jersey(size, price, stock, team_id, jersey_type_id);

        when(mockDAO.create(j)).thenReturn(j);

        //Act
        Jersey addedJersey = new Jersey();
        addedJersey = jerseyService.addNewJersey(size,price,stock,team_id,jersey_type_id);

        //Assert
        Assert.assertEquals(addedJersey,j);
    }

    @Test //If updateJersey method works correct then return the updated jersey
    public void updateJerseyShouldReturnUpdatedJersey (){
        //Arrange
        Jersey j = new Jersey("S",50.0,40,1,1);

        when(mockDAO.update(j)).thenReturn(j);
        //Act
        Jersey updatedJersey = jerseyService.updateJersey(j);

        //Assert
        Assert.assertEquals(updatedJersey,j);
    }

    @Test //If updateJersey method doesn't work correct then return null
    public void updateJerseyShouldReturnNull (){
        //Arrange
        Jersey j = new Jersey("S",50.0,40,1,1);

        when(mockDAO.update(j)).thenReturn(null);
        //Act
        Jersey updatedJersey = jerseyService.updateJersey(j);

        //Assert
        Assert.assertNull(updatedJersey);
    }

    @Test //If deleteJersey method works correct then return true
    public void deleteJerseyShouldReturnTrue (){
        //Arrange
        int jerseyId = 1;

        when(mockDAO.deleteById(jerseyId)).thenReturn(true);
        //Act
        boolean result = jerseyService.deleteJerseyById(jerseyId);

        //Assert
        Assert.assertTrue(result);
    }

    @Test //If deleteJersey method doesn't work correct then return false
    public void deleteJerseyShouldReturnFalse (){
        //Arrange
        int jerseyId = 1;

        when(mockDAO.deleteById(jerseyId)).thenReturn(false);
        //Act
        boolean result = jerseyService.deleteJerseyById(jerseyId);

        //Assert
        Assert.assertFalse(result);
    }






}
