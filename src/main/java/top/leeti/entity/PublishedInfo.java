package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishedInfo implements Serializable {
    @NotBlank(message = "id为空")
    @NotEmpty(message = "id为空")
    @NotNull(message = "id为空")
    private String id;
    private Integer iId;
    private String promulgatorId;
    @Max(9)
    @Min(1)
    private Integer typeId;
    private String description;
    @Min(value = 0, message = "图片数量不符合规范")
    @Max(value = 3, message = "图片数量不符合规范")
    private Integer pictureNum;
    private Integer likeNum;
    private Integer viewNum;
    private Integer commentNum;
    private Date gmtCreate;
    private Boolean Audit;
    private Boolean Available;
    private Boolean Anonymous;
    private String msg;
    private Date gmtClaim;
    private String claimantId;

    /*
     * 在数据库中没有以下属性
     */
    private String createTime;
    private String typeName;
    private List<String> pictureUrlList;

    private String avatarUrl;
    private String nickname;
    private Boolean Like;
    private String favoritedContentId;
}
