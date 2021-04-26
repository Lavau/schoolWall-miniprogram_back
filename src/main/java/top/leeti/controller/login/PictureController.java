package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.exception.MkdirCreateException;
import top.leeti.service.PublishedInfoService;
import top.leeti.service.UserService;
import top.leeti.util.FileUtil;
import top.leeti.util.WechatUtil;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController("loginPictureController")
public class PictureController {

    @Resource
    private PublishedInfoService publishedInfoService;

    @Resource
    private UserService userService;

    @PostMapping("/miniprogram/login/picture/save")
    public String savePictures(@RequestParam Integer typeId,  @RequestParam String id,
            @RequestParam MultipartFile picture) throws MkdirCreateException, IOException {
        log.info("图片：id: {}, typeId: {}", id, typeId);
        String originalFilename = picture.getOriginalFilename();
        picture.transferTo(FileUtil.createFile(typeId, id, originalFilename));

        Boolean verifyResult = WechatUtil.verifyPicture(typeId, id, originalFilename);

        Result<String> result = new Result<>();
        if (verifyResult) {
            result.setMsg("！！！图片含有敏感信息，不能发布！！！");
            result.setSuccess(false);

            publishedInfoService.deletePublishedInfo(id);
        } else {
            if (Integer.valueOf(0).equals(typeId)) {
                result.setMsg("ヾ(≧▽≦*)o：头像修改成功。。。");
                modifyAvatarUrl(originalFilename);
            }

            result.setSuccess(true);
        }

        return JSON.toJSONString(result);

    }

    private void modifyAvatarUrl(String fileName) {
        User user = User.obtainCurrentUser();
        String avatarUrl = FileUtil.obtainAvatarUrl(user.getOpenId(), fileName);
        user.setAvatarUrl(avatarUrl);
        userService.updateUser(user);
    }
}
