package top.leeti.myenum;

import lombok.Getter;

@Getter
public enum TypeEnum {
    /**
     * 个人发布
     * 感谢/吐槽
     * 失物招领
     * 寻求帮助
     * 脱单
     * 一卡通
     */
    PERSONAL_PUBLICITY(1, "personal_publicity"),
    THANK_OR_RIDICULE(3,"thank_or_ridicule"),
    LOST_AND_FOUND(4, "lost_and_found"),
    SEEK_HELP(5, "seek_help"),
    SINGLE(6, "single"),
    ECARD(7, "ecard");

    private Integer typeId;
    private String name;

    TypeEnum(Integer typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }
}