import com.revature.models.User;
import com.revature.repos.UserDAOImpl;
import com.revature.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    private UserDAOImpl mockDAO;

    @Before
    public void setup(){
        mockDAO = Mockito.mock(UserDAOImpl.class);

        userService = new UserService(mockDAO);
    }

    //Test for getByUsername Method
    @Test   //If a User with taken username exist in the DB then return the User
    public void takenUsernameShouldReturnUser (){
        //Arrange
        String username = "username";

        User u = new User("test","test","test", "test",username);

        when(mockDAO.getByUsername(username)).thenReturn(u);


        //Act
        User user = userService.getByUsername(username);

        //Assert
        Assert.assertNotNull(user);

    }

    @Test   //If there is not any User with the specific taken username then return null
    public void takenNotExistingUsernameShouldReturnNull (){
        //Arrange
        String username = "username";

        when(mockDAO.getByUsername(username)).thenReturn(null);

        //Act
        User user = userService.getByUsername(username);

        //Assert
        Assert.assertNull(user);
    }

    //Test for getById method
    @Test   //If a User id exists in the DB then return the User
    public void takenIdUserShouldReturnUser (){
        //Arrange
        int userId = 1;

        User u = new User("test","test","test", "test","username");
        u.setUser_id(userId);

        when(mockDAO.getById(userId)).thenReturn(u);

        //Act
        User user = userService.getById(userId);

        //Assert
        Assert.assertNotNull(user);
    }

    @Test   //If a User id doesn't exist in the DB then return null
    public void takenIdUserShouldReturnNull (){
        //Arrange
        int userId = 1;

        when(mockDAO.getById(userId)).thenReturn(null);

        //Act
        User user = userService.getById(userId);

        //Assert
        Assert.assertNull(user);
    }

    //Test for getAllUsers method
    @Test   //If there are Users in DB then return an Array List
    public void getAllUsersShouldReturnUsersArrayListStored(){
        //Arrange
        List<User> allUsers = new ArrayList<>();
        User u = new User();
        allUsers.add(u);

        boolean result = false;

        when(mockDAO.getAll()).thenReturn(allUsers);

        //Act
        if (!userService.getAllUsers().isEmpty()){
             result = true;
        }

        //Assert
        Assert.assertTrue(result);

    }
    @Test //If there are not any Users stored in the DB then return an empty array list
    public void getAllUsersShouldReturnEmptyUsersArrayList(){
        //Arrange
        List<User> allUsers = new ArrayList<>();

        boolean result = false;

        when(mockDAO.getAll()).thenReturn(allUsers);

        //Act
        if (userService.getAllUsers().isEmpty()){
            result = true;
        }

        //Assert
        Assert.assertTrue(result);
    }

    //Test for validateUsername method
    @Test   //If taken username don't pass the validation then return false
    public void takenUsernameShouldReturnFalse (){
        //Arrange
        String invalidUsername = "us";

        //Act
        boolean result = userService.validateUsername(invalidUsername);

        //Assert
        Assert.assertFalse(result);

    }

    @Test   //If taken username pass the validation then return true
    public void takenUsernameShouldReturnTrue (){
        //Arrange
        String validUsername = "Username12";

        //Act
        boolean result = userService.validateUsername(validUsername);

        //Assert
        Assert.assertTrue(result);

    }

    //Test for isUsernameAvailable method
    @Test //If taken username is available then return true
    public void availableUsernameShouldReturnTrue (){
        //Arrange
        String availableUsername = "username";

        when(mockDAO.getByUsername(availableUsername)).thenReturn(null);

        //Act
        boolean result = userService.isUsernameAvailable(availableUsername);

        //Assert
        Assert.assertTrue(result);
    }

    @Test //If taken username is not available then return false
    public void availableUsernameShouldReturnFalse (){
        //Arrange
        String availableUsername = "username";
        User u = new User();
        u.setUsername(availableUsername);

        when(mockDAO.getByUsername(availableUsername)).thenReturn(u);

        //Act
        boolean result = userService.isUsernameAvailable(availableUsername);

        //Assert
        Assert.assertFalse(result);
    }

    //Test for isEmailAvailable method
    @Test //If taken email is available then return true
    public void availableEmailShouldReturnTrue (){
        //Arrange
        String availableEmail = "email@email.com";

        when(mockDAO.getByEmail(availableEmail)).thenReturn(null);

        //Act
        boolean result = userService.isEmailAvailable(availableEmail);

        //Assert
        Assert.assertTrue(result);
    }

    @Test //If taken email is not available then return false
    public void availableEmailShouldReturnFalse (){
        //Arrange
        String availableEmail = "email@email.com";
        User u = new User();
        u.setEmail(availableEmail);

        when(mockDAO.getByEmail(availableEmail)).thenReturn(u);

        //Act
        boolean result = userService.isEmailAvailable(availableEmail);

        //Assert
        Assert.assertFalse(result);
    }

    @Test //If taken password pass the validation then return true
    public void takenPasswordPassValidation(){
        //Arrange
        String password = "Password";

        //Act
        boolean result = userService.validatePassword(password);

        //Assert
        Assert.assertTrue(result);
    }

    @Test //If taken password doesn't pass the validation then return false
    public void takenPasswordDoNotPassValidation(){
        //Arrange
        String password = "pass";

        //Act
        boolean result = userService.validatePassword(password);

        //Assert
        Assert.assertFalse(result);
    }

    @Test //If taken email pass the validation then return true
    public void takenEmailPassValidation(){
        //Arrange
        String email = "email@email.com";

        //Act
        boolean result = userService.validateEmail(email);

        //Assert
        Assert.assertTrue(result);
    }

    @Test //If taken email doesn't pass the validation then return false
    public void takenEmailDoNotPassValidation(){
        //Arrange
        String email = "email";

        //Act
        boolean result = userService.validateEmail(email);

        //Assert
        Assert.assertFalse(result);
    }

    @Test //If registerUser method is correct then return the registered user
    public void registerUserShouldReturnUser (){
        //Arrange
        String firstName= "test";
        String lastName= "test";
        String email= "test";
        String password= "test";
        String username= "test";

        User newUser = new User(firstName,lastName,email,password,username);

        when(mockDAO.create(newUser)).thenReturn(newUser);

        //Act
        User user = userService.registerUser(firstName,lastName,email,password,username);

        //Assert
        Assert.assertNotNull(user);

    }

    @Test //If user is logged in then return the user
    public void loginUserShouldReturnUser (){
        //Arrange
        String password = "Password";
        String email = "email@email.com";

        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());

        User u = new User();
        u.setPassword(hashPass);
        u.setEmail(email);

        when(mockDAO.getByEmail(email)).thenReturn(u);

        //Act
        User loggedUser = userService.loginUser(email,password);

        //Assert
        Assert.assertNotNull(loggedUser);
    }

    @Test //If credentials are wrong then return null;
    public void loginUserShouldReturnNull (){
        //Arrange
        String password = "Password";
        String email = "email@email.com";
        String wrongPassword = "Passsword";

        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());

        User u = new User();
        u.setPassword(hashPass);
        u.setEmail(email);

        when(mockDAO.getByEmail(email)).thenReturn(u);

        //Act
        User loggedUser = userService.loginUser(email,wrongPassword);

        //Assert
        Assert.assertNull(loggedUser);
    }

    @Test //If updatedUser method works correctly then return an updated user
    public void updatedUserShouldReturnUpdatedUser (){

        //Arrange
        User u = new User("test", "test", "test", "test", "test");
        when(mockDAO.update(u)).thenReturn(new User());
        //Act
        User updatedUser = userService.updateUser(u);
        //Assert
        Assert.assertNotNull(updatedUser);
    }

    @Test //If updatedUser method doesn't work correctly then return null
    public void updatedUserShouldReturnNull (){
        //Arrange
        User u = new User();
        when(mockDAO.update(u)).thenReturn(null);
        //Act
        User updatedUser = userService.updateUser(u);
        //Assert
        Assert.assertNull(updatedUser);
    }

    @Test //If resetPassword goes correct then return true
    public void resetPasswordShoulReturnTrue (){
        //Arrange
        String password = "password";
        int id = 2;

        when(mockDAO.resetPassword(password,id)).thenReturn(true);
        //Act
        boolean result = userService.resetPassword(password,id);
        //Assert
        Assert.assertTrue(result);
    }

    @Test //If resetPassword doesn't work correct then return false
    public void resetPasswordShouldReturnFalse (){
        //Arrange
        String password = "password";
        int id = 2;

        when(mockDAO.resetPassword(password,id)).thenReturn(false);
        //Act
        boolean result = userService.resetPassword(password,id);
        //Assert
        Assert.assertFalse(result);
    }



}
