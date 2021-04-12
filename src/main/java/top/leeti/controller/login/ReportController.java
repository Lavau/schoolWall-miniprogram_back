package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.Report;
import top.leeti.entity.ReportType;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.exception.RecordOfDisableOrDataBaseNoFoundException;
import top.leeti.service.PublishedInfoService;
import top.leeti.service.ReportService;
import top.leeti.util.UuidUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class ReportController {

    @Resource
    private ReportService reportService;

    @Resource
    private PublishedInfoService publishedInfoService;

    @GetMapping("/miniprogram/login/reportType/list")
    public String listReportTypes() {
        List<ReportType> reportTypes = reportService.listReportTypes();
        Result<List<ReportType>> result = new Result<>(null, null,reportTypes, true);
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/report")
    public String report(@Valid Report report, BindingResult validResult) {
        Result<String> result = new Result<>();
        if (validResult.hasErrors()) {
            result.setSuccess(false);
            result.setMsg("举报内容填写不规范");
        } else {
            PublishedInfo publishedInfo = publishedInfoService.getPublishedInfoById(report.getPublishedInfoId());
            if (publishedInfo == null) {
                throw new RecordOfDisableOrDataBaseNoFoundException("@=@：举报失败。。这条记录不存在或正在接受审核");
            } else {
                insertReport(report);

                result.setSuccess(true);
                result.setMsg("@^.^@：感谢您的举报！我们会尽快处理。。");
            }
        }
        return JSON.toJSONString(result);
    }

    private void insertReport(Report report) {
        report.setId(UuidUtil.acquireUuid());
        report.setGmtCreate(new Date());
        String reporterId = User.obtainCurrentUser().getStuId();
        report.setReporterId(reporterId);

        reportService.insertReport(report);
    }
}
