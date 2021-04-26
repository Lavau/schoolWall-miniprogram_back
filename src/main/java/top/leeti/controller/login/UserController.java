package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.service.UserService;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/miniprogram/login/nickname/modify")
    public String modifyNickname(@RequestParam String nickname) {
        User currentUser = User.obtainCurrentUser();
        currentUser.setNickname(nickname);
        currentUser.setGmtModified(new Date());
        userService.updateUser(currentUser);

        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setMsg("O(∩_∩)O：昵称修改好了。。。");
        return JSON.toJSONString(result);
    }
}
