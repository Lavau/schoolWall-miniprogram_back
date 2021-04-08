package top.leeti.controller.login;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.entity.Favorite;
import top.leeti.entity.FavoritedContent;
import top.leeti.entity.result.Result;
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

    @GetMapping("/miniprogram/login/favorite/create")
    public String createFavorite(@RequestParam String name) {
        Favorite favorite = new Favorite();
        favorite.setName(name);
        favoriteService.insertFavorite(favorite);
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setMsg("收藏夹创建成功！");
        return JSON.toJSONString(result);
    }

    @PostMapping("/miniprogram/login/favorite/collect")
    public String createFavorite(@RequestParam String favoriteId, @RequestParam String publishedInfoId) {
        FavoritedContent favoritedContent =
                new FavoritedContent(UuidUtil.acquireUuid(), favoriteId, publishedInfoId, new Date());
        favoriteService.insertFavoritedContent(favoritedContent);
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setMsg("收藏成功！");
        return JSON.toJSONString(result);
    }
}
