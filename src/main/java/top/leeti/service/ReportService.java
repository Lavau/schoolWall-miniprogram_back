package top.leeti.service;

import top.leeti.entity.Report;
import top.leeti.entity.ReportType;

import java.util.List;

public interface ReportService {
    List<ReportType> listReportTypes();

    void insertReport(Report report);
}
