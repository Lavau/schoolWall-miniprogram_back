package top.leeti.service;

import org.apache.ibatis.annotations.Param;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;
import top.leeti.entity.PublishedInfo;

import java.util.List;

public interface FavoriteService {
    List<Favorite> listFavorites();

    List<PublishedInfo> listFavoritedContentsByFavoriteId(String favoriteId);

    void insertFavorite(Favorite favorite);

    void insertFavoritedContent(FavoritedContent favoritedContent);

    Favorite getFavoriteByNameAndCreatorId(String name, String creatorId);

    Favorite getFavoriteById(String id);

    FavoritedContent getFavoritedContentById(String id);

    FavoritedContent getFavoritedContentByFavoriteIdAndPublishedInfoId(String favoriteId, String publishedInfoId);

    void deleteFavoriteById(String id);

    void deleteFavoritedContentById(String id);
}
