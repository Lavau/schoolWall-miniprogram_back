package top.leeti.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.*;
import top.leeti.entity.Comment;
import top.leeti.entity.Like;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.User;
import top.leeti.service.PublishedInfoService;
import top.leeti.util.FileUtil;
import top.leeti.util.TimeStampUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PublishedInfoServiceImpl implements PublishedInfoService {

    @Value("${page.size}")
    private int pageSize;

    @Resource
    private PublishedInfoMapper publishedInfoMapper;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private ReportMapper reportMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPublishedInfo(PublishedInfo publishedInfo) {
        publishedInfo.setPromulgatorId(User.obtainCurrentUser().getStuId());
        publishedInfo.setGmtCreate(new Date());
        publishedInfoMapper.insertPublishedInfo(publishedInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<PublishedInfo> listPublishedInfo(int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<PublishedInfo> list = publishedInfoMapper.listPublishedInfo();
        PageInfo<PublishedInfo> pageInfo =  new PageInfo<>(list);
        pageInfo.getList().forEach(item -> {
            if (new Integer(0).equals(item.getPictureNum())) {
                item.setPictureUrlList(new ArrayList<>(0));
            } else {
                item.setPictureUrlList(FileUtil.obtainListOfPictureUrl(item.getTypeId(), item.getId()));
            }
            item.setCreateTime(TimeStampUtil.timeStamp(item.getGmtCreate()));
        });
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PublishedInfo getPublishedInfoById(String id) {
        PublishedInfo publishedInfo = publishedInfoMapper.getPublishedInfoById(id);
        if (publishedInfo != null) {
            publishedInfo.setCreateTime(TimeStampUtil.timeStamp(publishedInfo.getGmtCreate()));
            Like like = likeMapper.getLikeByIdAndStuId(publishedInfo.getId(), User.obtainCurrentUser().getStuId());
            publishedInfo.setLike(like != null);
        }
        return publishedInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePublishedInfo(PublishedInfo publishedInfo) {
        publishedInfoMapper.updatePublishedInfo(publishedInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePublishedInfo(String id) {
        likeMapper.deleteLikeById(id);

        List<Comment> comments = commentMapper.listCommentsOfPublishedInfo(id);
        comments.forEach(comment -> commentMapper.deleteCommentByParentId(comment.getId()));

        favoriteMapper.deleteFavoritedContentByPublishedInfoId(id);

        reportMapper.deleteReportByPublishedInfoId(id);

        publishedInfoMapper.deletePublishedInfo(id);
    }
}
