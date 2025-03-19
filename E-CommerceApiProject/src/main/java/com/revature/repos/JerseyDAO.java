package com.revature.repos;

import com.revature.models.Jersey;

public interface JerseyDAO extends GeneralDAO<Jersey> {

    Jersey getByTeamName (String name);
}
