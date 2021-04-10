package top.leeti.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.LikeMapper;
import top.leeti.dao.PublishedInfoMapper;
import top.leeti.entity.Like;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.User;
import top.leeti.exception.RecordOfDisableOrDataBaseNoFoundException;
import top.leeti.service.LikeService;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private PublishedInfoMapper publishedInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(Boolean isLike, String id) throws RecordOfDisableOrDataBaseNoFoundException {
        PublishedInfo publishedInfo = publishedInfoMapper.getPublishedInfoById(id);
        if (publishedInfo == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("@=@：点赞失败。。。这条记录不存在或者正在接受审核");
        } else {
            if (isLike) {
                like(publishedInfo);
            } else {
                cancelLike(publishedInfo);
            }
        }
    }

    private void cancelLike(PublishedInfo publishedInfo) {
        String stuId = User.obtainCurrentUser().getStuId();
        likeMapper.deleteLikeByIdAndStuId(publishedInfo.getId(), stuId);
        publishedInfo.setLikeNum(publishedInfo.getLikeNum() - 1);
        publishedInfoMapper.updatePublishedInfo(publishedInfo);
    }

    private void like(PublishedInfo publishedInfo) {
        String stuId = User.obtainCurrentUser().getStuId();
        Like like = new Like(publishedInfo.getId(), stuId, new Date());
        likeMapper.insertLike(like);
        publishedInfo.setLikeNum(publishedInfo.getLikeNum() + 1);
        publishedInfoMapper.updatePublishedInfo(publishedInfo);
    }
}
