package top.leeti.controller.nologin;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import top.leeti.entity.result.Result;
import top.leeti.util.WechatUtil;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/miniprogram/noLogin/")
public class LoginController {
    
    @PostMapping("login")
    public String login(@RequestParam String code) {
        Map<String, String> map = WechatUtil.acquireSessionKeyAndOpenId(code);
        String openId = map.get("openId");

        Result<String> result;

        if (openId == null) {
            result = new Result<>(null, "获取openId失败", null, false);
        } else {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(openId, "");
            Subject currentUser = SecurityUtils.getSubject();
            try{
                currentUser.login(usernamePasswordToken);
                currentUser.isRemembered();
                result = new Result<>(null, null, "registered", true);
            } catch(AccountException accountException) {
                result = new Result<>(null, null, "unregistered", false);
                return JSON.toJSONString(result);
            }
        }

        return JSON.toJSONString(result);
    }

    @GetMapping("error")
    public String error() {
        Result<String> result = new Result<>();
        result.setSuccess(false);
        result.setMsg("小程序登录失败！！建议您点击右上角，选择重新进入小程序");
        return JSON.toJSONString(result);
    }
}
