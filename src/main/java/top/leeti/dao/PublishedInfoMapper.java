package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.PublishedInfo;

import java.util.List;

@Mapper
public interface PublishedInfoMapper {

    @Insert("INSERT _published_info (_id, _promulgator_id, _type_id, _description, _picture_num, _gmt_create, " +
            "_is_anonymous, _msg) VALUES (#{pi.id}, #{pi.promulgatorId}, #{pi.typeId}, #{pi.description}, " +
            "#{pi.pictureNum}, #{pi.gmtCreate}, #{pi.Anonymous}, #{pi.msg})")
    void insertPublishedInfo(@Param("pi") PublishedInfo publishedInfo);

    @Select("SELECT pi._id AS id, _promulgator_id AS promulgatorId, u._avatar_url AS avatarUrl, u._nickname AS " +
            "nickname, _type_id AS typeId, _chinese_name AS typeName, _description AS description, _picture_num " +
            "AS pictureNum, _like_num AS likeNum, _view_num AS viewNum, _comment_num AS commentNum, pi._gmt_create " +
            "AS gmtCreate, pi._is_available AS Available, _is_anonymous AS Anonymous, _is_audit AS Audit, _msg AS " +
            "msg, _gmt_claim AS gmtClaim, _claimant_id AS claimantId FROM _published_info AS pi LEFT JOIN _type ON " +
            "pi._type_id = _type._id LEFT JOIN _user AS u ON u._stu_id = pi._promulgator_id WHERE _is_audit = 1 AND " +
            "pi._is_available = 1 ORDER BY pi._gmt_create DESC")
    List<PublishedInfo> listPublishedInfo();

    @Select("SELECT pi._id AS id, _promulgator_id AS promulgatorId, u._avatar_url AS avatarUrl, u._nickname AS " +
            "nickname, _type_id AS typeId, _chinese_name AS typeName, _description AS description, _picture_num " +
            "AS pictureNum, _like_num AS likeNum, _view_num AS viewNum, _comment_num AS commentNum, pi._gmt_create " +
            "AS gmtCreate, pi._is_available AS Available, _is_anonymous AS Anonymous, _is_audit AS Audit, _msg AS " +
            "msg, _gmt_claim AS gmtClaim, _claimant_id AS claimantId FROM _published_info AS pi LEFT JOIN _type ON " +
            "pi._type_id = _type._id LEFT JOIN _user AS u ON u._stu_id = pi._promulgator_id WHERE _is_audit = 1 AND " +
            "pi._is_available = 1 AND pi._id = #{id}")
    PublishedInfo getPublishedInfoById(@Param("id") String id);

    @Update("UPDATE _published_info SET _like_num = #{pi.likeNum}, _view_num = #{pi.viewNum}, _comment_num = " +
            "#{pi.commentNum}, _is_available = #{pi.Available}, _is_audit = #{pi.Audit}, _gmt_claim = " +
            "#{pi.gmtClaim}, _claimant_id = #{pi.claimantId} WHERE _id = #{pi.id}")
    void updatePublishedInfo(@Param("pi") PublishedInfo publishedInfo);

    @Select("SELECT COUNT(_id) FROM _published_info WHERE _promulgator_id = #{promulgatorId}")
    Integer getTotalNumOfPublishedInfoByPromulgatorId(@Param("promulgatorId") String promulgatorId);

    @Delete("DELETE FROM _published_info WHERE _id = #{id}")
    void deletePublishedInfo(@Param("id") String id);

}
