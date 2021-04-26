package top.leeti.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.*;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.User;
import top.leeti.service.ObtainMyDataService;
import top.leeti.util.TimeStampUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ObtainMyDataServiceImpl implements ObtainMyDataService {

    @Value("${page.size}")
    private int pageSize;

    @Resource
    private ObtainMyDataMapper obtainMyDataMapper;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private PublishedInfoMapper publishedInfoMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private MsgMapper msgMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<PublishedInfo> obtainMyDataByTypeId(Integer typeId, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<PublishedInfo> list = obtainMyDataMapper.listObtainDataByTypeId(typeId);
        PageInfo<PublishedInfo> pageInfo = new PageInfo<>(list);
        pageInfo.getList().forEach(publishedInfo ->
                publishedInfo.setCreateTime(TimeStampUtil.timeStamp(publishedInfo.getGmtCreate()))
        );
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> obtainSomeInformationOfUser() {
        Map<String, Object> infos = new HashMap<>(7);
        String promulgatorId = User.obtainCurrentUser().getStuId();
        User currentUser = User.obtainCurrentUser();
        infos.put("avatarUrl", currentUser.getAvatarUrl());
        infos.put("nickname", currentUser.getNickname());
        infos.put("id", currentUser.getOpenId());
        infos.put("publishedInfoTotalNum", publishedInfoMapper.getTotalNumOfPublishedInfoByPromulgatorId(promulgatorId).toString());
        infos.put("likeTotalNum", likeMapper.getLikeTotalNumByPromulgatorId(promulgatorId).toString());
        infos.put("commentTotalNum", commentMapper.getCommentTotalNumByPromulgatorId(promulgatorId).toString());
        infos.put("unreadMsgNum", msgMapper.listUnreadMsgByReceiverId(promulgatorId).size());
        return infos;
    }
}
