package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.User;

@Mapper
public interface UserMapper {

    @Insert("INSERT _user (_stu_id, _stu_name, _college_id, _avatar_url, _nickname, _gmt_create, _en_password, _open_id) " +
            "VALUES (#{user.stuId}, #{user.stuName}, #{user.collegeId}, #{user.avatarUrl}, #{user.nickname}, " +
            "#{user.gmtCreate}, #{user.enPassword}, #{user.openId})")
    boolean insertUser(@Param("user") User user);

    @Select("SELECT _open_id AS openId, _stu_id AS stuId, _stu_name AS stuName, _college_id AS collegeId, _avatar_url " +
            "AS avatarUrl, _nickname AS nickname, _gmt_create AS gmtCreate, _gmt_modified AS gmtModified, _en_password " +
            "AS enPassword, _is_available AS Available from _user where _stu_id = #{stuId}")
    User getUserByStuId(@Param("stuId") String stuId);

    @Select("SELECT _open_id AS openId, _stu_id AS stuId, _stu_name AS stuName, _college_id AS collegeId, _avatar_url " +
            "AS avatarUrl, _nickname AS nickname, _gmt_create AS gmtCreate, _gmt_modified AS gmtModified, _en_password " +
            "AS enPassword, _is_available AS Available from _user where _open_id = #{openId} ")
    User getUserByOpenId(@Param("openId") String openId);

    @Update("UPDATE _user SET _nickname = #{u.nickname}, _avatar_url = #{u.avatarUrl}, " +
            "_gmt_modified = #{u.gmtModified} WHERE _open_id = #{u.openId}")
    void updateUser(@Param("u") User user);
}
