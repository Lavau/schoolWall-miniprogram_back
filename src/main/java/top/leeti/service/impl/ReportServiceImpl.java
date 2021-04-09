package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.PublishedInfoMapper;
import top.leeti.dao.ReportMapper;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.Report;
import top.leeti.entity.ReportType;
import top.leeti.service.ReportService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private PublishedInfoMapper publishedInfoMapper;

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
        publishedInfoMapper.updatePublishedInfo(publishedInfo);
    }
}
