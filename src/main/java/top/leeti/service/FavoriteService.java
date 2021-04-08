package top.leeti.service;

import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;

import java.util.List;

public interface FavoriteService {
    List<Favorite> listFavorites();

    void insertFavorite(Favorite favorite);

    void insertFavoritedContent(FavoritedContent favoritedContent);
}
