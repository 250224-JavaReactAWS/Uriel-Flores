package com.revature.repos;

import com.revature.models.Team;
import com.revature.models.User;

public interface TeamDAO extends GeneralDAO<Team> {

    Team getByTeamName(String teamName);
}
