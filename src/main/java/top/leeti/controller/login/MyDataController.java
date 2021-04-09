package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.result.Result;
import top.leeti.service.ObtainMyDataService;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
public class MyDataController {

    @Resource
    private ObtainMyDataService obtainMyDataService;

    @GetMapping("/miniprogram/login/myData/info")
    public String obtainMyInfo() {
        Map<String, String> infos = obtainMyDataService.obtainSomeInformationOfUser();
        Result<Map<String, String>> result = new Result<>();
        result.setSuccess(true);
        result.setData(infos);
        return JSON.toJSONString(result);
     }

    @GetMapping("/miniprogram/login/myData")
    public String obtainMyData(@RequestParam Integer typeId, @RequestParam Integer pageNum) {
        PageInfo<PublishedInfo> pageInfo = obtainMyDataService.obtainMyDataByTypeId(typeId, pageNum);
        Result.MyPage<PublishedInfo> page = new Result.MyPage<>(pageInfo.getPageNum(), pageInfo.getPages(), pageInfo.getList());
        Result<Result.MyPage<PublishedInfo>> result = new Result<>();
        result.setData(page);
        return JSON.toJSONString(result);
    }
}

