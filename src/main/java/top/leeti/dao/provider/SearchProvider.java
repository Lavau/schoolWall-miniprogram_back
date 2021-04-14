package top.leeti.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import top.leeti.controller.nologin.SearchController;
import top.leeti.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchProvider {

    public String search(SearchController.SearchCondition searchCondition){
        return new SQL() {
            {
                SELECT("pi._id AS id", "_promulgator_id AS promulgatorId", "u._avatar_url AS avatarUrl",
                        "u._nickname AS nickname", "_type_id AS typeId", "_chinese_name AS typeName", "_description AS description",
                        "_picture_num AS pictureNum", "_like_num AS likeNum", "_view_num AS viewNum", "_comment_num AS commentNum",
                        "pi._gmt_create AS gmtCreate", "pi._is_available AS Available", "_is_anonymous AS Anonymous",
                        "_is_audit AS Audit", "_msg AS msg", "_gmt_claim AS gmtClaim", "_claimant_id AS claimantId ");
                FROM("_published_info AS pi");
                LEFT_OUTER_JOIN("_type ON pi._type_id = _type._id");
                LEFT_OUTER_JOIN("_user AS u ON u._stu_id = pi._promulgator_id");
                WHERE().WHERE("_is_audit = 1 AND pi._is_available = 1");
                if (searchCondition.getSearchText() != null && searchCondition.getSearchText().length() > 0) {
                    WHERE().WHERE("pi._description = '" + searchCondition.getSearchText() + "'");
                }
                if (searchCondition.getTypeId() != null) {
                    WHERE().WHERE("pi._type_id = " + searchCondition.getTypeId());
                }
                if ((searchCondition.getBeginDate() != null && searchCondition.getBeginDate().length() > 0)
                    && (searchCondition.getEndDate() != null && searchCondition.getEndDate().length() > 0)) {
                    WHERE().WHERE("pi._gmt_create BETWEEN '" + searchCondition.getBeginDate() + "' AND '" +
                            searchCondition.getEndDate() + "'");
                } else if (searchCondition.getBeginDate() != null && searchCondition.getBeginDate().length() > 0) {
                    WHERE().WHERE("pi._gmt_create BETWEEN '" + searchCondition.getBeginDate() + "' AND '" +
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'");
                }
                WHERE().ORDER_BY("pi._gmt_create DESC");
            }
        }.toString();
    }

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
