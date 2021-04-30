package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.Comment;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.Report;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.exception.RecordOfDisableOrDataBaseNoFoundException;
import top.leeti.service.CommentService;
import top.leeti.service.PublishedInfoService;
import top.leeti.service.ReportService;
import top.leeti.util.TimeStampUtil;
import top.leeti.util.UuidUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private ReportService reportService;

    @Resource
    private PublishedInfoService publishedInfoService;

    @PostMapping("/miniprogram/login/comment/insert")
    public String addCommentOfMixedData(@RequestParam String commentContent, @RequestParam String attachedId) {
        PublishedInfo publishedInfo = publishedInfoService.getPublishedInfoById(attachedId);
        if (publishedInfo == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("无法评论。本条记录已被删除或者正在接受审核");
        } else {
            insertComment(commentContent, attachedId);

            publishedInfo.setCommentNum(publishedInfo.getCommentNum() + 1);
            publishedInfoService.updatePublishedInfo(publishedInfo);

            Result<Boolean> result = new Result<>();
            result.setSuccess(true);
            result.setMsg("评论成功");
            return JSON.toJSONString(result);
        }
    }

    private void insertComment(String commentContent, String attachedId) {
        String id = UuidUtil.acquireUuid();
        Comment comment = new Comment();
        comment.setId(id);
        comment.setPromulgatorId(User.obtainCurrentUser().getStuId());
        comment.setAttachedId(attachedId);
        comment.setParentId(id);
        comment.setContent(commentContent);
        comment.setGmtCreate(new Date());

        commentService.insert(comment);
    }

    @PostMapping("/miniprogram/login/comment/list/typeData")
    public String listCommentsOfMixedData(@RequestParam int pageNum,
                                          @RequestParam String attachedId) {
        PageInfo<Comment> pageInfo = commentService.listCommentsOfMixedData(attachedId, pageNum);
        return retrieveResultOfCommentList(pageInfo);
    }

    @PostMapping("/miniprogram/login/comment/delete/typeData")
    public String deleteCommentOfMixedData(@RequestParam String parentId) {
        Comment comment = commentService.getCommentById(parentId);
        if (comment == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("!! 这条评论已被删除或接受审核 !!");
        } else {
            commentService.deleteCommentByParentId(parentId);

            Result<List<Comment>> result = new Result<>();
            result.setSuccess(true);
            result.setMsg("评论删除成功");
            return JSON.toJSONString(result);
        }
    }

    @PostMapping("/miniprogram/login/comment/reply/insert")
    public String addRepliedComment(@RequestParam String commentContent, @RequestParam String parentId) {
        verifyBeforeInsertReplyComment(parentId);

        insertReplyComment(commentContent, parentId);

        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setMsg("回复评论成功！");
        return JSON.toJSONString(result);
    }

    private void verifyBeforeInsertReplyComment(String parentId) {
        Comment parentComment = commentService.getCommentById(parentId);
        if (parentComment == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("回复评论失败。。。该回复评论的父评论不存在");
        }

        PublishedInfo publishedInfo = publishedInfoService.getPublishedInfoById(parentComment.getAttachedId());
        if (publishedInfo == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("评论失败。。。本条记录已被删除或者正在接受审核");
        }
    }

    private void insertReplyComment(String commentContent, String parentId) {
        String id = UuidUtil.acquireUuid();
        Comment comment = new Comment();
        comment.setId(id);
        comment.setPromulgatorId(User.obtainCurrentUser().getStuId());
        comment.setParentId(parentId);
        comment.setContent(commentContent);
        comment.setGmtCreate(new Date());

        commentService.insert(comment);
    }

    @PostMapping("/miniprogram/login/comment/reply/list")
    public String listCommentsOfRepliedComment(@RequestParam int pageNum,
                                               @RequestParam String parentId) {
        Comment comment = commentService.getCommentById(parentId);
        if (comment == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("暂无法查看");
        }

        PageInfo<Comment> pageInfo = commentService.listCommentsOfRepliedComment(parentId, pageNum);
        return retrieveResultOfCommentList(pageInfo);
    }

    private String retrieveResultOfCommentList(PageInfo<Comment> pageInfo) {
        pageInfo.getList().forEach(comment -> comment.setCreateTime(TimeStampUtil.timeStamp(comment.getGmtCreate())));
        Result.MyPage<Comment> page = new Result.MyPage<>(pageInfo.getPageNum(), pageInfo.getPages(), pageInfo.getList());
        Result<Result.MyPage<Comment>> result = new Result<>(null, null, page, true);
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/comment/reply/delete")
    public String deleteCommentOfRepliedComment(@RequestParam String id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("! ! 该回复评论已被删除 ! !");
        } else {
            commentService.deleteCommentById(id);

            Result<String> result = new Result<>();
            result.setSuccess(true);
            result.setMsg("@-@：本条回复评论删除成功！");
            return JSON.toJSONString(result);
        }
    }

    @PostMapping("/miniprogram/login/comment/report")
    public String reportComment(@RequestParam String id, @RequestParam String reportReason) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("举报评论失败。。。该评论已不能查看");
        }

        insertReport(id, reportReason);

        comment.setAvailable(false);
        comment.setAudit(false);
        commentService.updateComment(comment);

        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setMsg("(￣y▽￣)╭ ：本条评论举报成功！我们会很快处理。。");
        return JSON.toJSONString(result);
    }

    private void insertReport(String id, String reportReason) {
        Report report = new Report();
        report.setId(UuidUtil.acquireUuid());
        report.setCommentId(id);
        report.setReporterId(User.obtainCurrentUser().getStuId());
        report.setReportReason(reportReason);
        report.setGmtCreate(new Date());
        reportService.insertReport(report);
    }
}