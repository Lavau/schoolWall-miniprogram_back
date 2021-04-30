package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.Report;
import top.leeti.entity.ReportType;

import java.util.List;

@Mapper
public interface ReportMapper {

    @Select("SELECT _id AS id, _name AS name, _gmt_create AS gmtCreate FROM _report_type ORDER BY _id ASC")
    List<ReportType> listReportTypes();

    @Select("SELECT _id AS id, _published_info_id AS publishedInfoId, _comment_id AS commentId, _reporter_id AS reporterId," +
            "_report_reason AS reportReason, _is_audit AS Audit, _gmt_create AS gmtCreate FROM _report WHERE _id = #{id}")
    ReportType getReportById(@Param("id") Integer id);

    @Insert("INSERT _report (_id, _published_info_id, _comment_id, _reporter_id, _report_reason, _gmt_create) " +
            "VALUES (#{r.id}, #{r.publishedInfoId}, #{r.commentId}, #{r.reporterId}, #{r.reportReason}, " +
            "#{r.gmtCreate})")
    void insertReport(@Param("r") Report report);

    @Delete("DELETE FROM _report WHERE _published_info_id = #{publishedInfoId}")
    void deleteReportByPublishedInfoId(@Param("publishedInfoId") String publishedInfoId);
}
