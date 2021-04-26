package top.leeti.service;

import top.leeti.entity.User;

public interface UserService {

    boolean insertUser(User user);

    User getUserByStuId(String stuId);

    User getUserByOpenId(String openId);

    void updateUser(User user);

}
