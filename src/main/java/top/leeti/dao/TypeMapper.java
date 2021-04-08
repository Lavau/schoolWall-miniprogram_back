package top.leeti.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.leeti.entity.Type;

import java.util.List;

@Mapper
public interface TypeMapper {
    @Select("SELECT _id AS id, _chinese_name AS chineseName, _english_name AS englishName " +
            "From _type WHERE _is_available = 1 ORDER BY _id ASC")
    List<Type> listTypes();
}
