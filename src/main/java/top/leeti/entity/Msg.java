package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    private String id;
    private String receiverId;
    private String content;
    private Date gmtCreate;
    private boolean Read;
}
