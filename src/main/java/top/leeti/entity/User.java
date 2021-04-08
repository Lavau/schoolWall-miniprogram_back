package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.SecurityUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    public static User obtainCurrentUser() {
        return (User)SecurityUtils.getSubject().getPrincipal();
    }

    private String openId;

    @NotBlank(message = "学生名字为空")
    @NotEmpty(message = "学生名字为空")
    @NotNull(message = "学生名字为空")
    private String stuName;

    @NotBlank(message = "学号为空")
    @NotEmpty(message = "学号为空")
    @NotNull(message = "学号为空")
    private String stuId;

    @NotBlank(message = "学院号为空")
    @NotEmpty(message = "学院号为空")
    @NotNull(message = "学院号为空")
    private String collegeId;

    @NotBlank(message = "头像url为空")
    @NotEmpty(message = "头像url为空")
    @NotNull(message = "头像url为空")
    private String avatarUrl;

    @NotBlank(message = "昵称为空")
    @NotEmpty(message = "昵称为空")
    @NotNull(message = "昵称为空")
    private String nickname;

    private Date gmtCreate;
    @NotBlank(message = "密码为空")
    @NotEmpty(message = "密码为空")
    @NotNull(message = "密码为空")
    private String enPassword;
    private Boolean Available;

    private Integer likeTotalNum;
    private Integer commentTotalNum;
}
