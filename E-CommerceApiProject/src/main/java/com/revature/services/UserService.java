package com.revature.services;

import com.revature.models.User;
import com.revature.repos.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private UserDAO userDAO;

    //Constructor
    public UserService (UserDAO userDAO){
        this.userDAO = userDAO;
    }

    // Method getByUsername (only admin)
    public User getByUsername (String username){
        return userDAO.getByUsername(username);
    }

    public User getById (int id){
        return userDAO.getById(id);
    }

    //Method getAllUsers (only admin)
    public List<User> getAllUsers (){
        return userDAO.getAll();
    }

    //Method to validate min length of username
    public boolean validateUsername (String username){
        return username.length() >= 8;
    }

    //Method to validate availability of username
    public boolean isUsernameAvailable (String username){
        return userDAO.getByUsername(username) == null;
    }

    //Method to validate availability of email
    public boolean isEmailAvailable (String email){
        return userDAO.getByEmail(email) == null;
    }

    //Method to validate min length and if it has or not lowercase/uppercase character.
    public boolean validatePassword (String password){

        boolean correctLength = password.length() >= 8;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;

        for (Character c : password.toCharArray()){
            if (Character.isUpperCase(c)){
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
        }

        return correctLength && hasUpperCase && hasLowerCase;
    }

    //Method to hash password
    public String hashPass (String password){
        //Hashing pass
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //Method to validate an Email
    public boolean validateEmail (String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    //Method to register new user
    public User registerUser (String firstName, String lastname, String email, String password, String username){

        User newUser = new User(firstName, lastname, email, password, username);

        //Save user into Database
        return userDAO.create(newUser);
    }

    //Method to log in a user
    public User loginUser (String email, String password){

        User returnedUser = userDAO.getByEmail(email);

        //If the user doesn't exist then return null
        if (returnedUser == null){
            return null;
        }

        //Verify that the password matches
        if (BCrypt.checkpw(password, returnedUser.getPassword())){
            return returnedUser;
        }
        return null;
    }

    //Method to update a user
    public User updateUser (User updatedUser){
        return userDAO.update(updatedUser);
    }




}
