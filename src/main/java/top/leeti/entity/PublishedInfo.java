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
    protected String id;
    protected Integer iId;
    protected String promulgatorId;
    @Max(9)
    @Min(1)
    protected Integer typeId;
    protected String description;
    @Min(value = 0, message = "图片数量不符合规范")
    @Max(value = 3, message = "图片数量不符合规范")
    protected Integer pictureNum;
    protected Integer likeNum;
    protected Integer viewNum;
    protected Integer commentNum;
    protected Date gmtCreate;
    protected Boolean Audit;
    protected Boolean Available;
    protected Boolean Anonymous;
    protected String msg;
    private Date gmtClaim;
    private String claimantId;

    /*
     * 在数据库中没有以下属性
     */
    protected String createTime;
    protected String typeName;
    protected List<String> pictureUrlList;

    private String avatarUrl;
    private String nickname;
    private Boolean Like;
    private String favoritedContentId;
}
