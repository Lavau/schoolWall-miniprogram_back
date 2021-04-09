package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.Report;
import top.leeti.entity.ReportType;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
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
            result.setMsg("参数错误");
        } else {
            if (report.getReportReason().length() == 0 && report.getReportTypeId() == null) {
                result.setSuccess(false);
                result.setMsg("参数错误");
            } else {
                report.setId(UuidUtil.acquireUuid());
                report.setGmtCreate(new Date());
                report.setReporterId(User.obtainCurrentUser().getStuId());
                reportService.insertReport(report);
                result.setSuccess(true);
                result.setMsg("感谢您的举报！我们会尽快处理。。");
            }
        }
        return JSON.toJSONString(result);
    }

}
