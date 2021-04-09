package top.leeti.service;

import com.github.pagehelper.PageInfo;
import top.leeti.entity.PublishedInfo;

import java.util.Map;

public interface ObtainMyDataService {

    PageInfo<PublishedInfo> obtainMyDataByTypeId(Integer typeId, Integer pageNum);

    Map<String, Object> obtainSomeInformationOfUser();
}
