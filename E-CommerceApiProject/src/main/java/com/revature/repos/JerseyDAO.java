package com.revature.repos;

import com.revature.models.Jersey;

import java.util.List;

public interface JerseyDAO extends GeneralDAO<Jersey> {

    List<Jersey> getByTeamName (String name);



}
