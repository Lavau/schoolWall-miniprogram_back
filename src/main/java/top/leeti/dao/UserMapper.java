package top.leeti.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.leeti.entity.User;

@Mapper
public interface UserMapper {

    @Insert("INSERT _user (_stu_id, _stu_name, _college_id, _avatar_url, _nickname, _gmt_create, _en_password, _open_id) " +
            "VALUES (#{user.stuId}, #{user.stuName}, #{user.collegeId}, #{user.avatarUrl}, #{user.nickname}, " +
            "#{user.gmtCreate}, #{user.enPassword}, #{user.openId})")
    boolean insertUser(@Param("user") User user);

    @Select("SELECT _open_id as openId, _stu_name as stuName, _stu_id as stuId, _college_id as collegeId, _avatar_url " +
            "as avatarUrl, _nickname AS nickname, _gmt_create as gmtCreate from _user where _stu_id = #{stuId} " +
            "AND _en_password = #{enPassword}")
    User getUserByStuIdAndPassword(@Param("stuId") String stuId, @Param("enPassword") String enPassword);

    @Select("SELECT _open_id as openId, _stu_name as stuName, _stu_id as stuId, _college_id as collegeId, _avatar_url " +
            "as avatarUrl, _nickname AS nickname, _gmt_create as gmtCreate from _user where _open_id = #{openId} ")
    User getUserByOpenId(@Param("openId") String openId);
}
