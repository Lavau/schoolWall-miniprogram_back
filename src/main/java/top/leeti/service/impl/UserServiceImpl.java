package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.UserMapper;
import top.leeti.entity.User;
import top.leeti.service.UserService;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserByStuId(String stuId) {
        return userMapper.getUserByStuId(stuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserByOpenId(String openId) {
        return userMapper.getUserByOpenId(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
