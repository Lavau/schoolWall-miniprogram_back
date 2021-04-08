package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ecard implements Serializable {
    private String id;

    private Integer iId;
    private String promulgatorId;
    private String claimantId;

    @NotBlank(message = "学院名为空")
    @NotEmpty(message = "学院名为空")
    @NotNull(message = "学院名为空")
    private String college;

    @NotBlank(message = "该一卡通的学号为空")
    @NotEmpty(message = "该一卡通的学号为空")
    @NotNull(message = "该一卡通的学号为空")
    @Size(max = 9, message = "stuId 不符合规范")
    private String stuId;

    @NotBlank(message = "该一卡通的学生号为空")
    @NotEmpty(message = "该一卡通的学生号为空")
    @NotNull(message = "该一卡通的学生号为空")
    private String stuName;

    @NotBlank(message = "该一卡通的id为空")
    @NotEmpty(message = "该一卡通的id为空")
    @NotNull(message = "该一卡通的id为空")
    @Size(max = 10, message = "ecard 不符合规范")
    private String ecardId;

    @NotBlank(message = "该一卡通的认领信息为空")
    @NotEmpty(message = "该一卡通的认领信息为空")
    @NotNull(message = "该一卡通的认领信息为空")
    @Size(max = 50, message = "msg 不符合规范")
    private String msg;

    private Date gmtCreate;
    private Date gmtClaim;
    private Date gmtModified;


    /*
     * 在数据库中没有以下属性
     */
    private String promulgatorName;
    private String claimantName;
}
