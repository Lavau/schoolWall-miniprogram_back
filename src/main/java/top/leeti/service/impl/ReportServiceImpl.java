package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.MsgMapper;
import top.leeti.dao.PublishedInfoMapper;
import top.leeti.dao.ReportMapper;
import top.leeti.entity.Msg;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.Report;
import top.leeti.entity.ReportType;
import top.leeti.service.ReportService;
import top.leeti.util.TimeStampUtil;
import top.leeti.util.UuidUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private PublishedInfoMapper publishedInfoMapper;

    @Resource
    private MsgMapper msgMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ReportType> listReportTypes() {
        return reportMapper.listReportTypes();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertReport(Report report) {
        reportMapper.insertReport(report);

        PublishedInfo publishedInfo = publishedInfoMapper.getPublishedInfoById(report.getPublishedInfoId());
        publishedInfo.setAvailable(false);
        publishedInfo.setAudit(false);
        publishedInfoMapper.updatePublishedInfo(publishedInfo);

        Msg msg = new Msg();
        msg.setId(UuidUtil.acquireUuid());
        msg.setGmtCreate(new Date());
        msg.setReceiverId(publishedInfo.getPromulgatorId());
        msg.setContent(generateContent(report, publishedInfo));
        msgMapper.insertMsg(msg);
    }

    private String generateContent(Report report, PublishedInfo publishedInfo) {
        String content = "您在 " + TimeStampUtil.timeStamp(publishedInfo.getGmtCreate()) + " 发布的内容被举报。举报原因：";
        if (report.getReportTypeId() != null) {
            ReportType reportType = reportMapper.getReportTypeById(report.getReportTypeId());
            content = content + reportType.getName();
            if (report.getReportReason() != null && report.getReportReason().length() > 0) {
                content = content + "、" + report.getReportReason();
            }
        } else {
            content = content + report.getReportReason();
        }
        content = content + "。我们会对本条内容进行审核。审核期间，本条内容无法查看。";
        return content;
    }
}
