package top.leeti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private String id;
    private String creatorId;
    private String name;
    private Date gmtCreate;
}
