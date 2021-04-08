package top.leeti.service;

import com.github.pagehelper.PageInfo;
import top.leeti.entity.Comment;

public interface CommentService {

    void insert(Comment comment);

    PageInfo<Comment> listCommentsOfMixedData(String attachedId, int pageNum);

    void deleteCommentByParentId(String parentId);

    Comment getCommentById(String id);

    PageInfo<Comment> listCommentsOfRepliedComment(String parentId, int pageNum);

    void deleteCommentById(String id);
}
