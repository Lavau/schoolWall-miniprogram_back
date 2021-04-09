package top.leeti.service;

import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;

import java.util.List;

public interface FavoriteService {
    List<Favorite> listFavorites();

    List<FavoritedContent> listFavoritedContentsByFavoriteId(String favoriteId);

    void insertFavorite(Favorite favorite);

    void insertFavoritedContent(FavoritedContent favoritedContent);

    Favorite getFavoriteByNameAndCreatorId(String name, String creatorId);

    FavoritedContent getFavoritedContentByFavoriteIdAndPublishedInfoId(String favoriteId, String publishedInfoId);

    void deleteFavoriteById(String id);

    void deleteFavoritedContentById(String id);
}
