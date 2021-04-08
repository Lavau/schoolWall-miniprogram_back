package top.leeti.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import top.leeti.entity.User;

public class ObtainMyDataProvider {

    public String listObtainDataByTypeId(Integer typeId){
        return new SQL() {
            {
                String currentStuId = User.obtainCurrentUser().getStuId();
                SELECT("pi._id AS id", "_chinese_name AS typeName", "_description AS description",
                        "_picture_num AS pictureNum", "pi._gmt_create AS gmtCreate");
                FROM("_published_info AS pi");
                LEFT_OUTER_JOIN("_type ON pi._type_id = _type._id");
                WHERE().WHERE("_is_audit = 1 AND pi._is_available = 1");
                if (Integer.valueOf(1).equals(typeId)) {
                    WHERE().WHERE("_promulgator_id = '" + currentStuId + "'");
                } else if (Integer.valueOf(2).equals(typeId)) {
                    WHERE().WHERE("pi._id IN (SELECT DISTINCT _id FROM _like WHERE _stu_id = '" + currentStuId + "')");
                } else if (Integer.valueOf(3).equals(typeId)) {
                    WHERE().WHERE("pi._id IN (SELECT DISTINCT _attached_id FROM _comment " +
                            "WHERE pi._promulgator_id = '" + currentStuId + "' AND _attached_id IS NOT NULL)");
                }
                WHERE().ORDER_BY("pi._gmt_create DESC");
            }
        }.toString();
    }
}
