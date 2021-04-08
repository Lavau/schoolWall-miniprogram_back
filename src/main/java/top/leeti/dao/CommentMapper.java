package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("SELECT _id AS id, _promulgator_id AS promulgatorId, _attached_id As attachedId, _parent_id AS parentId, " +
            "_content AS content, _comment._gmt_create AS gmtCreate, _avatar_url AS avatarUrl, _nickname AS nickname " +
            "FROM _comment LEFT JOIN _user ON _user._stu_id = _comment._promulgator_id WHERE _is_available = 1 AND " +
            "_is_audit = 1 AND _attached_id = #{attachedId} ORDER BY _comment._gmt_create DESC")
    List<Comment> listCommentsOfPublishedInfo(@Param("attachedId") String attachedId);

    @Select("SELECT _id AS id, _promulgator_id AS promulgatorId, _attached_id As attachedId, _parent_id AS parentId, " +
            "_content AS content, _comment._gmt_create AS gmtCreate, _avatar_url AS avatarUrl, _nickname AS nickname " +
            "FROM _comment LEFT JOIN _user ON _user._stu_id = _comment._promulgator_id WHERE _is_available = 1 AND " +
            "_is_audit = 1 AND _parent_id = #{parentId} ORDER BY _comment._gmt_create ASC")
    List<Comment> listCommentsOfRepliedComment(@Param("parentId") String parentId);

    @Select("SELECT count(_id) FROM _comment WHERE _parent_id = #{parentId}")
    int getCommentNumByParentId(@Param("parentId") String parentId);

    @Insert("INSERT _comment (_id, _promulgator_id, _attached_id, _parent_id, _content, _gmt_create) VALUES " +
            "(#{comment.id}, #{comment.promulgatorId}, #{comment.attachedId}, #{comment.parentId}, " +
            "#{comment.content}, #{comment.gmtCreate})")
    void insertComment(@Param("comment") Comment comment);

    @Delete ("DELETE FROM _comment WHERE _parent_id = #{parentId}")
    void deleteCommentByParentId(@Param("parentId") String parentId);

    @Delete ("DELETE FROM _comment WHERE _id = #{id}")
    void deleteCommentById(@Param("id") String id);

    @Select("SELECT _id AS id, _promulgator_id AS promulgatorId, _attached_id As attachedId, _parent_id AS parentId, " +
            "_content AS content, _comment._gmt_create AS gmtCreate, _avatar_url AS avatarUrl, _nickname AS nickname " +
            "FROM _comment LEFT JOIN _user ON _user._stu_id = _comment._promulgator_id WHERE _id = #{id}")
    Comment getCommentById(@Param("id") String id);

    @Select("SELECT COUNT(_id) FROM _comment WHERE _attached_id IN (SELECT _id FROM _published_info WHERE _promulgator_id = #{promulgatorId})")
    Integer getCommentTotalNumByPromulgatorId(@Param("promulgatorId") String promulgatorId);
}
