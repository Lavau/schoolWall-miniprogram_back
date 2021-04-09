package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;
import top.leeti.entity.PublishedInfo;
import top.leeti.entity.User;
import top.leeti.entity.result.Result;
import top.leeti.exception.RecordOfDataBaseNoFoundException;
import top.leeti.service.FavoriteService;
import top.leeti.util.UuidUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @GetMapping("/miniprogram/login/favorite/list")
    public String listFavorites() {
        List<Favorite> favorites = favoriteService.listFavorites();
        Result<List<Favorite>> result = new Result<>();
        result.setSuccess(true);
        result.setData(favorites);
        return JSON.toJSONString(result);
    }

    @GetMapping("/miniprogram/login/favoritedContent/list")
    public String listFavoritedContents(@RequestParam String favoriteId) {
        List<PublishedInfo> favoritedContents = favoriteService.listFavoritedContentsByFavoriteId(favoriteId);
        Result<List<PublishedInfo>> result = new Result<>();
        result.setSuccess(true);
        result.setData(favoritedContents);
        return JSON.toJSONString(result);
    }

    @GetMapping("/miniprogram/login/favorite/create")
    public String createFavorite(@RequestParam String name) {
        Favorite favorite = favoriteService.getFavoriteByNameAndCreatorId(name, User.obtainCurrentUser().getStuId());
        Result<String> result = new Result<>();
        if (favorite == null) {
            String creatorId = User.obtainCurrentUser().getStuId();
            Favorite newFavorite = new Favorite(UuidUtil.acquireUuid(), creatorId, name, new Date());
            favoriteService.insertFavorite(newFavorite);
            result.setSuccess(true);
            result.setMsg("收藏夹创建成功！");
        } else {
            result.setSuccess(false);
            result.setMsg("收藏夹已存在了哟！");
        }
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/favorite/collect")
    public String createFavorite(@RequestParam String favoriteId, @RequestParam String publishedInfoId) {
        FavoritedContent favoritedContent = favoriteService.getFavoritedContentByFavoriteIdAndPublishedInfoId(favoriteId, publishedInfoId);
        Result<String> result = new Result<>();
        if (favoritedContent == null) {
            FavoritedContent newFavoritedContent =
                    new FavoritedContent(UuidUtil.acquireUuid(), favoriteId, publishedInfoId, new Date());
            favoriteService.insertFavoritedContent(newFavoritedContent);
            result.setSuccess(true);
            result.setMsg("收藏成功！");
        } else {
            result.setSuccess(false);
            result.setMsg("已收藏过了。。");
        }
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/favorite/delete")
    public String deleteFavorite(@RequestParam String id) {
        Favorite favorite = favoriteService.getFavoriteById(id);
        if (favorite == null) {
            throw new RecordOfDataBaseNoFoundException("@_@: 这个收藏夹已经被删除了。。", id, "/miniprogram/login/favorite/delete");
        } else {
            favoriteService.deleteFavoriteById(id);
            Result<String> result = new Result<>();
            result.setSuccess(true);
            result.setMsg("该收藏夹删除成功！");
            return JSON.toJSONString(result);
        }
    }

    @PostMapping("/miniprogram/login/favoritedContent/delete")
    public String deleteFavoritedContent(@RequestParam String favoritedContentId) {
        if (favoriteService.getFavoritedContentById(favoritedContentId) == null ) {
            throw new RecordOfDataBaseNoFoundException("@_@: 这个收藏已经被删除了。。", favoritedContentId, "/miniprogram/login/favoritedContent/delete");
        } else {
            favoriteService.deleteFavoritedContentById(favoritedContentId);
            Result<String> result = new Result<>();
            result.setSuccess(true);
            result.setMsg("该收藏删除成功！");
            return JSON.toJSONString(result);
        }
    }
}
