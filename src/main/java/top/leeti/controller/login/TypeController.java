package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.Type;
import top.leeti.entity.result.Result;
import top.leeti.service.TypeService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping(value = {"/miniprogram/login/type/obtain", "/miniprogram/noLogin/type/obtain"})
    public String obtainTypes() {
        List<Type> types = typeService.listTypes();
        Result<List<Type>> result = new Result<>();
        result.setSuccess(true);
        result.setData(types);
        return JSON.toJSONString(result);
    }
}
