package top.leeti.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.CommentMapper;
import top.leeti.entity.Comment;
import top.leeti.entity.User;
import top.leeti.service.CommentService;
import top.leeti.service.PublishedInfoService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Value("${page.size}")
    private int pageSize;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private PublishedInfoService publishedInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<Comment> listCommentsOfMixedData(String attachedId, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.listCommentsOfPublishedInfo(attachedId);
        comments.forEach(comment -> {
            comment.setCommentNum(commentMapper.getCommentNumByParentId(comment.getId()));
            comment.setMine(comment.getPromulgatorId().equals(User.obtainCurrentUser().getStuId()));
        });
        return new PageInfo<>(comments);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentByParentId(String parentId) {
        commentMapper.deleteCommentByParentId(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Comment getCommentById(String id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<Comment> listCommentsOfRepliedComment(String parentId, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.listCommentsOfRepliedComment(parentId);
        comments.forEach(comment -> comment.setMine(comment.getPromulgatorId().equals(User.obtainCurrentUser().getStuId())));
        return new PageInfo<>(comments);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentById(String id) {
        commentMapper.deleteCommentById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }
}
