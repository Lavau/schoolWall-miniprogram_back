package top.leeti.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import top.leeti.dao.provider.ObtainMyDataProvider;
import top.leeti.entity.PublishedInfo;

import java.util.List;

@Mapper
public interface ObtainMyDataMapper {
    @SelectProvider(type = ObtainMyDataProvider.class, method = "listObtainDataByTypeId")
    List<PublishedInfo> listObtainDataByTypeId(@Param("typeId") Integer typeId);
}
