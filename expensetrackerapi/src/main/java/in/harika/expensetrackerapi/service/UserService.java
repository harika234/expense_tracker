package in.harika.expensetrackerapi.service;

import in.harika.expensetrackerapi.entity.User;
import in.harika.expensetrackerapi.entity.UserModel;

public interface UserService {


    User createUser(UserModel user);

    User read();

    User update(UserModel  user);

    void delete();
    User getLoggedInUser();
}
