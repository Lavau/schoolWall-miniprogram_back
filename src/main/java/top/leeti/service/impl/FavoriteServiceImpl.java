package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.FavoriteMapper;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.User;
import top.leeti.service.FavoriteService;
import top.leeti.util.TimeStampUtil;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Favorite> listFavorites() {
        return favoriteMapper.listFavoritesByCreatorId(User.obtainCurrentUser().getStuId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PublishedInfo> listFavoritedContentsByFavoriteId(String favoriteId) {
        List<PublishedInfo> favoritedContents = favoriteMapper.listFavoritedContentsByFavoriteId(favoriteId);
        favoritedContents.forEach(item -> item.setCreateTime("收藏: " + TimeStampUtil.timeStamp(item.getGmtCreate())));
        return favoritedContents;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertFavorite(Favorite favorite) {
        favoriteMapper.insertFavorite(favorite);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertFavoritedContent(FavoritedContent favoritedContent) {
        favoriteMapper.insertFavoritedContent(favoritedContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Favorite getFavoriteByNameAndCreatorId(String name, String creatorId) {
        return favoriteMapper.getFavoriteByNameAndCreatorId(name, creatorId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Favorite getFavoriteById(String id) {
        return favoriteMapper.getFavoriteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FavoritedContent getFavoritedContentById(String id) {
        return favoriteMapper.getFavoritedContentById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FavoritedContent getFavoritedContentByFavoriteIdAndPublishedInfoId(String favoriteId, String publishedInfoId) {
        return favoriteMapper.getFavoritedContentByFavoriteIdAndPublishedInfoId(favoriteId, publishedInfoId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavoriteById(String id) {
        List<PublishedInfo> favoritedContents = favoriteMapper.listFavoritedContentsByFavoriteId(id);
        favoritedContents.forEach(item -> favoriteMapper.deleteFavoritedContentById(item.getFavoritedContentId()));
        favoriteMapper.deleteFavoriteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavoritedContentById(String id) {
        favoriteMapper.deleteFavoritedContentById(id);
    }

}
