package top.leeti.controller.nologin;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.service.UserService;
import top.leeti.util.WechatUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class RegisterController {

    @Resource
    private UserService userService;

    @PostMapping("/miniprogram/noLogin/register")
    public String register(@Valid User user, BindingResult verifyResult, @RequestParam String code) {
        Result<String> result = new Result<>();

        if (verifyResult.hasErrors()) {
            result.setSuccess(false);
            result.setMsg("注册失败！原因为填写不规范");
            return JSON.toJSONString(result);
        }

        User registerEdUser = userService.getUserByStuId(user.getStuId());
        if (registerEdUser != null) {
            result.setSuccess(false);
            result.setMsg("注册失败！原因为当前学号已存在。。。");
        } else {
            Map<String, String> map = WechatUtil.acquireSessionKeyAndOpenId(code);
            String openId = map.get("openId");

            if (openId == null) {
                result.setSuccess(false);
                result.setMsg("注册失败！原因为openId获取失败");
            } else {
                user.setOpenId(openId);
                user.setGmtCreate(new Date());
                userService.insertUser(user);
                log.info("用户{}注册成功！", user.toString());
                result.setSuccess(true);
                result.setMsg("恭喜您，注册成功！请登录");
            }
        }
        return JSON.toJSONString(result);
    }
}
