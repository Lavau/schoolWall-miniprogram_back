package top.leeti.controller.nologin;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.College;
import top.leeti.entity.result.Result;
import top.leeti.myenum.ResultCodeEnum;
import top.leeti.service.CollegeService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class CollegeController {

    @Resource
    private CollegeService collegeService;

    @GetMapping("/miniprogram/noLogin/college/list")
    public String listCollege() {
        Result<List<College>> result = new Result<>(null, null, collegeService.listCollege(), null);
        return JSON.toJSONString(result);
    }
}
