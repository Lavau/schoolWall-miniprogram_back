package top.leeti.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.leeti.entity.College;

import java.util.List;

@Mapper
public interface CollegeMapper {

    @Select("select _college_id as collegeId, _college_name as collegeName from _college " +
            "where _is_available = 1 order by _college_id asc")
    List<College> listCollege();
}
