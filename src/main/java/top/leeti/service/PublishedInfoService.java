package top.leeti.service;

import com.github.pagehelper.PageInfo;
import top.leeti.entity.PublishedInfo;

public interface PublishedInfoService {
    void insertPublishedInfo(PublishedInfo publishedInfo);

    PageInfo<PublishedInfo> listPublishedInfo(int pageNum);

    PublishedInfo getPublishedInfoById(String id);

    void updatePublishedInfo(PublishedInfo publishedInfo);

    void deletePublishedInfo(String id);
}
