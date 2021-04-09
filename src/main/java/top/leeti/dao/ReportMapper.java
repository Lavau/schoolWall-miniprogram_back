package top.leeti.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.leeti.entity.Report;
import top.leeti.entity.ReportType;

import java.util.List;

@Mapper
public interface ReportMapper {

    @Select("SELECT _id AS id, _name AS name, _gmt_create AS gmtCreate FROM _report_type ORDER BY _id ASC")
    List<ReportType> listReportTypes();

    @Insert("INSERT _report (_id, _published_info_id, _reporter_id, _report_type_id, _report_reason, _gmt_create) " +
            "VALUES (#{r.id}, #{r.publishedInfoId}, #{r.reporterId}, #{r.reportTypeId}, #{r.reportReason}, #{r.gmtCreate})")
    void insertReport(@Param("r") Report report);
}
