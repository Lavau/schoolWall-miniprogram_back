package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.Like;

@Mapper
public interface LikeMapper {
    @Insert("INSERT _like (_id, _stu_id, _gmt_create) VALUES (#{like.id}, #{like.stuId}, #{like.gmtCreate})")
    void insertLike(@Param("like")Like like);

    @Delete("DELETE FROM _like WHERE _id = #{id} AND _stu_id = #{stuId}")
    void deleteLikeByIdAndStuId(@Param("id") String id, @Param("stuId") String stuId);

    @Delete("DELETE FROM _like WHERE _id = #{id}")
    void deleteLikeById(@Param("id") String id);

    @Select("SELECT _id AS id, _stu_id AS stuId, _gmt_create AS gmtCreate FROM _like WHERE _id = #{id} AND _stu_id = #{stuId}")
    Like getLikeByIdAndStuId(@Param("id") String id, @Param("stuId") String stuId);

    @Select("SELECT COUNT(_id) FROM _like WHERE _id IN (SELECT _id FROM _published_info WHERE _promulgator_id = #{promulgatorId})")
    Integer getLikeTotalNumByPromulgatorId(@Param("promulgatorId") String promulgatorId);
}
