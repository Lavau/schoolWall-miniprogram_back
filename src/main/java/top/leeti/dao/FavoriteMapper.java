package top.leeti.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("SELECT _id AS id, _creator_id AS creatorId, _name AS name, _gmt_create AS gmtCreate FROM _favorite " +
            "WHERE _creator_id = #{creatorId} ORDER BY _gmt_create ASC")
    List<Favorite> listFavoritesByCreatorId(@Param("creatorId") String creatorId);

    @Insert("INSERT _favorite (_id, _creator_id, _name, _gmt_create) VALUES (#{f.id}, #{f.creatorId}, #{f.name}, " +
            "#{f.gmtCreate})")
    void insertFavorite(@Param("f") Favorite favorite);

    @Insert("INSERT _favorited_content (_id, _favorite_id, _published_info_id, _gmt_create) VALUES " +
            "(#{fc.id}, #{fc.favoriteId}, #{fc.publishedInfoId}, #{fc.gmtCreate})")
    void insertFavoritedContent(@Param("fc") FavoritedContent favoritedContent);
}
