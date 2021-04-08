package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.result.Result;
import top.leeti.service.LikeService;

import javax.annotation.Resource;

@Slf4j
@RestController
public class LikeController {

    @Resource
    private LikeService likeService;

    @PostMapping("/miniprogram/login/like")
    public String likeOrCancelLike(@RequestParam Boolean isLike, @RequestParam String id) {
        likeService.like(isLike, id);

        Result<Boolean> result = new Result<>();
        result.setSuccess(true);
        result.setMsg(isLike ? "点赞成功" : "取消点赞成功");
        return JSON.toJSONString(result);
    }
}
