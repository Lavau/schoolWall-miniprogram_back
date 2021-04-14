package top.leeti.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.leeti.controller.nologin.SearchController.SearchCondition;
import top.leeti.dao.SearchMapper;
import top.leeti.entity.PublishedInfo;
import top.leeti.service.SearchService;
import top.leeti.util.FileUtil;
import top.leeti.util.TimeStampUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${page.size}")
    private int pageSize;

    @Resource
    private SearchMapper searchMapper;

    @Override
    public PageInfo<PublishedInfo> listPublishedInfos(SearchCondition searchCondition, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<PublishedInfo> list = searchMapper.listPublishedInfos(searchCondition);
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
}
