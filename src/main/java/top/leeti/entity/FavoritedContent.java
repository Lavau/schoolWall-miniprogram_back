package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritedContent {
    private String id;
    private String favoriteId;
    private String publishedInfoId;
    private Date gmtCreate;
}
