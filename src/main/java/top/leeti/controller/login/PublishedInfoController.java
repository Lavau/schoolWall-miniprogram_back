package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.exception.RecordOfDisableOrDataBaseNoFoundException;
import top.leeti.service.PublishedInfoService;
import top.leeti.util.FileUtil;
import top.leeti.util.SenInfoCheckUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@RestController
public class PublishedInfoController {

    @Resource
    private PublishedInfoService publishedInfoService;

    @PostMapping("/miniprogram/login/publish")
    public String publish(@Valid PublishedInfo publishedInfo, BindingResult resultsOfVerification) {
        Result<String> result = new Result<>();
        if (resultsOfVerification.hasErrors()) {
            String description = publishedInfo.getDescription();
            if ((description == null || description.length() == 0)
                    && Integer.valueOf(0).equals(publishedInfo.getPictureNum())) {
                result.setSuccess(false);
                result.setMsg("发布的数据有错误");
            }
        } else {
            publishedInfoService.insertPublishedInfo(publishedInfo);

            result.setSuccess(true);
            result.setMsg("您的内容发布成功，快去首页刷新看看吧QvQ!");
        }
        return JSON.toJSONString(result);
    }

    @GetMapping("/miniprogram/noLogin/publishedInfo/list")
    public String obtainIndexPageData(@RequestParam(defaultValue = "1") int pageNum) {
        PageInfo<PublishedInfo> pageInfo = publishedInfoService.listPublishedInfo(pageNum);

        Result.MyPage<PublishedInfo> page = new Result.MyPage<>(pageInfo.getPageNum(), pageInfo.getPages(), pageInfo.getList());
        Result<Result.MyPage<PublishedInfo>> result = new Result<>(null, null, page, null);
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/publishedInfo/detail")
    public String obtainDetailInfo(@RequestParam String id) {
        PublishedInfo publishedInfo  = publishedInfoService.getPublishedInfoById(id);
        if (publishedInfo == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("本条发布的信息无法查看。可能已被删除或者接受审核");
        } else {
            Result<PublishedInfo> result = new Result<>();

            if (Integer.valueOf(0).equals(publishedInfo.getPictureNum())) {
                publishedInfo.setPictureUrlList(new ArrayList<>(0));
            } else {
                publishedInfo.setPictureUrlList(FileUtil.obtainListOfPictureUrl(publishedInfo.getTypeId(), publishedInfo.getId()));
            }

            publishedInfo.setViewNum(publishedInfo.getViewNum() + 1);
            publishedInfoService.updatePublishedInfo(publishedInfo);

            result.setData(publishedInfo);
            result.setSuccess(true);
            return JSON.toJSONString(result);
        }

    }

    @PostMapping("/miniprogram/login/publishedInfo/claim")
    public String claimThing(@RequestParam String id) {
        PublishedInfo publishedInfo  = publishedInfoService.getPublishedInfoById(id);
        if (publishedInfo == null) {
            throw new RecordOfDisableOrDataBaseNoFoundException("#-#：认领失败。。该物品可能已被认领或者已被删除或者正在接受审核");
        } else {
            publishedInfo.setGmtClaim(new Date());
            publishedInfo.setClaimantId(User.obtainCurrentUser().getStuId());
            publishedInfo.setAvailable(false);
            publishedInfoService.updatePublishedInfo(publishedInfo);

            Result<String> result = new Result<>();
            result.setSuccess(true);
            result.setMsg("@H.H@：认领成功~~~");
            return JSON.toJSONString(result);
        }

    }

    @PostMapping("/miniprogram/login/publishedInfo/delete")
    public String deleteTypeDataInfoToDatabase(@RequestParam String id) {
        publishedInfoService.deletePublishedInfo(id);

        Result<PublishedInfo> result = new Result<>();
        result.setMsg("本条信息删除成功！");
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/publishedInfo/description/verify")
    public String verifyDescription(@RequestParam String description) {
//        Integer verifyResult = WechatUtil.verifyDescription(description);
        Boolean verifyResult = SenInfoCheckUtil.cotentFilter(description);

        Result<Boolean> result = new Result<>();
        result.setData(verifyResult);
        return JSON.toJSONString(result);
    }
}
