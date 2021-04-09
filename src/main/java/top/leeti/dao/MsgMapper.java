package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.Msg;

import java.util.List;

@Mapper
public interface MsgMapper {
    @Insert("INSERT _msg (_id, _receiver_id, _content, _gmt_create) VALUES " +
            "(#{m.id}, #{m.receiverId}, #{m.content}, #{m.gmtCreate})")
    void insertMsg(@Param("m") Msg msg);

    @Update("UPDATE _msg SET _is_read = 1 WHERE _id = #{id}")
    void updateReadOfMsgById(@Param("id") String id);

    @Select("SELECT _id AS id, _receiver_id AS receiverId, _content AS content, _gmt_create AS gmtCreate " +
            "FROM _msg WHERE _receiver_id = #{receiverId} AND _is_read = 0 ORDER By _gmt_create DESC")
    List<Msg> listUnreadMsgByReceiverId(@Param("receiverId") String receiverId);
}
