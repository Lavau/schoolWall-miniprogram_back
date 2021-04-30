package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private String id;
    @NotBlank(message = "publishedInfoId为空")
    @NotEmpty(message = "publishedInfoId为空")
    @NotNull(message = "publishedInfoId为空")
    private String publishedInfoId;

    private String commentId;

    private String reporterId;
    @NotBlank(message = "举报原因为空")
    @NotEmpty(message = "举报原因为空")
    @NotNull(message = "举报原因为空")
    private String reportReason;
    private Boolean Audit;
    private Date gmtCreate;
}
