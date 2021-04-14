package top.leeti.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import top.leeti.controller.nologin.SearchController.SearchCondition;
import top.leeti.dao.provider.SearchProvider;
import top.leeti.entity.PublishedInfo;

import java.util.List;

@Mapper
public interface SearchMapper {

    @SelectProvider(type = SearchProvider.class, method = "search")
    List<PublishedInfo> listPublishedInfos(SearchCondition searchCondition);
}
