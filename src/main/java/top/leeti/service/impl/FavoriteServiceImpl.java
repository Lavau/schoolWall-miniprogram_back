package top.leeti.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.leeti.dao.FavoriteMapper;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;
import top.leeti.entity.User;
import top.leeti.service.FavoriteService;
import top.leeti.util.UuidUtil;

import javax.annotation.Resource;
import java.util.Date;
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
    public void insertFavorite(Favorite favorite) {
        favorite.setId(UuidUtil.acquireUuid());
        favorite.setGmtCreate(new Date());
        favorite.setCreatorId(User.obtainCurrentUser().getStuId());
        favoriteMapper.insertFavorite(favorite);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertFavoritedContent(FavoritedContent favoritedContent) {
        favoriteMapper.insertFavoritedContent(favoritedContent);
    }

}
