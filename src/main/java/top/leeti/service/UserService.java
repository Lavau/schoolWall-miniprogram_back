package top.leeti.service;

import top.leeti.entity.User;

public interface UserService {

    boolean insertUser(User user);

    User getUserByStuIdAndPassword(String stuId, String enPassword);

    User getUserByOpenId(String openId);

}
