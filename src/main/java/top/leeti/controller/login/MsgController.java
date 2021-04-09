package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.Msg;
import top.leeti.entity.result.Result;
import top.leeti.service.MsgService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class MsgController {

    @Resource
    private MsgService msgService;

    @GetMapping("/miniprogram/login/msg/unread/list")
    public String listUnreadMsg() {
        List<Msg> unReadMsgs = msgService.listUnreadMsg();
        Result<List<Msg>> result = new Result<>();
        result.setSuccess(true);
        result.setData(unReadMsgs);
        return JSON.toJSONString(result);
    }
}
