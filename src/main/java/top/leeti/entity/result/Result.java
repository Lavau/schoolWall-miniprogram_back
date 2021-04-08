package top.leeti.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private String code;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * 返回的泛型结果
     */
    private T data;

    private Boolean success;

    @Data
    @AllArgsConstructor
    public static class MyPage<P> {
        private int pageNum;
        private int pages;
        private List<P> list;
    }
}
