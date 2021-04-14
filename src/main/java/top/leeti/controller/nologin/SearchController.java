package top.leeti.controller.nologin;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.result.Result;
import top.leeti.service.SearchService;

import javax.annotation.Resource;

@Slf4j
@RestController
public class SearchController {

    @Resource
    private SearchService searchService;

    @PostMapping("/miniprogram/noLogin/search")
    public String search(SearchCondition searchCondition, Integer pageNum) {
        boolean verifyResult = verifySearchCondition(searchCondition);
        log.info("{}", searchCondition.toString());
        if (!verifyResult) {
            Result<String> result = new Result<>(null, "填写的搜索条件错误", null, false);
            return JSON.toJSONString(result);
        } else {
            PageInfo<PublishedInfo> pageInfo = searchService.listPublishedInfos(searchCondition, pageNum);
            Result.MyPage<PublishedInfo> page = new Result.MyPage<>(pageInfo.getPageNum(), pageInfo.getPages(), pageInfo.getList());
            Result<Result.MyPage<PublishedInfo>> result = new Result<>(null, null, page, true);
            return JSON.toJSONString(result);
        }
    }

    private boolean verifySearchCondition(SearchCondition searchCondition) {
        return (searchCondition.searchText != null && searchCondition.searchText.length() > 0)
                || (searchCondition.beginDate != null && searchCondition.beginDate.length() > 0)
                || (searchCondition.endDate != null && searchCondition.endDate.length() > 0)
                || searchCondition.typeId != null;
    }

    @Data
    public static class SearchCondition {
        private Integer typeId;
        private String searchText;
        private String beginDate;
        private String endDate;
    }
}
