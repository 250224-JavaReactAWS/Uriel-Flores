package com.revature.repos;

import com.revature.models.User;

public interface UserDAO extends GeneralDAO<User> {

    User getByUsername (String username);

    User getByEmail (String email);

    Boolean resetPassword (String password, int id);

}
