package top.leeti.service;

import com.github.pagehelper.PageInfo;
import top.leeti.controller.nologin.SearchController.SearchCondition;
import top.leeti.entity.PublishedInfo;

public interface SearchService {

    PageInfo<PublishedInfo> listPublishedInfos(SearchCondition searchCondition, Integer pageNum);
}
