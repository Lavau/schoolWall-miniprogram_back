package top.leeti.service;

import top.leeti.entity.College;

import java.util.List;

/**
 * @author leet
 * @date 2020/12/1 17:22
 */
public interface CollegeService {
    /**
     * 获取全部的学院信息（不带主键）
     * @return
     */
    List<College> listCollege();
}
