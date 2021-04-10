package top.leeti.dao;

import org.apache.ibatis.annotations.*;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;
import top.leeti.entity.PublishedInfo;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("SELECT _id AS id, _creator_id AS creatorId, _name AS name, _gmt_create AS gmtCreate FROM _favorite " +
            "WHERE _creator_id = #{creatorId} ORDER BY _gmt_create ASC")
    List<Favorite> listFavoritesByCreatorId(@Param("creatorId") String creatorId);

    @Select("SELECT pi._id AS id, _chinese_name AS typeName, _description AS description, _picture_num AS pictureNum, " +
            "fc._gmt_create AS gmtCreate, fc._id AS favoritedContentId FROM _favorited_content AS fc LEFT OUTER JOIN " +
            "_published_info AS pi ON pi._id = fc._published_info_id LEFT OUTER JOIN _type ON pi._type_id = _type._id " +
            "WHERE pi._is_audit = 1 AND pi._is_available = 1 AND fc._favorite_id = #{favoriteId} ORDER BY " +
            "fc._gmt_create DESC")
    List<PublishedInfo> listFavoritedContentsByFavoriteId(@Param("favoriteId") String favoriteId);

    @Select("SELECT _id AS id FROM _favorited_content WHERE _favorite_id = #{favoriteId} ")
    List<String> listIdOfFavoritedContentsByFavoriteId(@Param("favoriteId") String favoriteId);

    @Insert("INSERT _favorite (_id, _creator_id, _name, _gmt_create) VALUES (#{f.id}, #{f.creatorId}, #{f.name}, " +
            "#{f.gmtCreate})")
    void insertFavorite(@Param("f") Favorite favorite);

    @Insert("INSERT _favorited_content (_id, _favorite_id, _published_info_id, _gmt_create) VALUES " +
            "(#{fc.id}, #{fc.favoriteId}, #{fc.publishedInfoId}, #{fc.gmtCreate})")
    void insertFavoritedContent(@Param("fc") FavoritedContent favoritedContent);

    @Select("SELECT _id AS id, _creator_id AS creatorId, _name AS name, _gmt_create AS gmtCreate FROM _favorite " +
            "WHERE _creator_id = #{creatorId} AND _name = #{name}")
    Favorite getFavoriteByNameAndCreatorId(@Param("name") String name, @Param("creatorId") String creatorId);

    @Select("SELECT _id AS id, _creator_id AS creatorId, _name AS name, _gmt_create AS gmtCreate " +
            "FROM _favorite WHERE _id = #{id}")
    Favorite getFavoriteById(@Param("id") String id);

    @Select("SELECT _id AS id, _favorite_id AS favoriteId, _published_info_id AS publishedInfoId, " +
            "_gmt_create AS gmtCreate FROM _favorited_content WHERE _id = #{id}")
    FavoritedContent getFavoritedContentById(@Param("id") String id);

    @Select("SELECT _id AS id, _favorite_id AS favoriteId, _published_info_id AS publishedInfoId, _gmt_create AS " +
            "gmtCreate FROM _favorited_content WHERE _favorite_id = #{favoriteId} AND _published_info_id = " +
            "#{publishedInfoId}")
    FavoritedContent getFavoritedContentByFavoriteIdAndPublishedInfoId(
            @Param("favoriteId") String favoriteId, @Param("publishedInfoId") String publishedInfoId);

    @Delete("DELETE FROM _favorite WHERE _id = #{id}")
    void deleteFavoriteById(@Param("id") String id);

    @Delete("DELETE FROM _favorited_content WHERE _id = #{id}")
    void deleteFavoritedContentById(@Param("id") String id);

    @Delete("DELETE FROM _favorited_content WHERE _published_info_id = #{publishedInfoId}")
    void deleteFavoritedContentByPublishedInfoId(@Param("publishedInfoId") String publishedInfoId);
}
