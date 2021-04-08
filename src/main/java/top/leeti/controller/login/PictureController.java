package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.leeti.entity.result.Result;
import top.leeti.exception.MkdirCreateException;
import top.leeti.util.FileUtil;

import java.io.IOException;

@Slf4j
@RestController("loginPictureController")
public class PictureController {

    @PostMapping("/miniprogram/login/picture/save")
    public String savePictures(@RequestParam Integer typeId,  @RequestParam String id,
            @RequestParam MultipartFile picture) {
        log.info("图片：id: {}, typeId: {}", id, typeId);
        try {
            picture.transferTo(FileUtil.createFile(typeId, id, picture.getOriginalFilename()));
            Result<Boolean> result = new Result<>(null, "图片保存成功", true, null);
            return JSON.toJSONString(result);
        } catch (MkdirCreateException | IOException exception) {
            Result<Boolean> result = new Result<>(null, "图片保存失败！", false, null);
            return JSON.toJSONString(result);
        }

    }
}
