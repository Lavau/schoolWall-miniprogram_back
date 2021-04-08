package top.leeti.myenum;

import lombok.Getter;

/**
 * description 处理结果code枚举
 *      W0XXX 为自定义状态码
 *      其余采用《阿里泰山版开发手册》中的状态码
 **/
@Getter
public enum ResultCodeEnum {

    /**
     * code: W0600
     * explanation: 验证出错
     * 含义：JSR-303 校验出错
     */
    VALID_ERROR("W0600", "验证出错"),
    /**
     * code: W0601
     * explanation: 图片保存失败
     */
    PICTURE_SAVE_UNSUCCESSFULLY("W0601", "图片保存失败"),
    /**
     * code: W0602
     * explanation: 登录成功
     */
    LOGIN_SUCCESSFULLY("W0602", "登录成功"),
    /**
     * code: W0603
     * explanation: 登录失败，请检查填写
     */
    LOGIN_UNSUCCESSFULLY("W0603", "登录失败，请检查填写"),
    /**
     * code: W0604
     * explanation: 无权访问
     */
    NO_ACCESS("W0604", "无权访问"),
    /**
     * code: W0605
     * explanation: 已被认领
     */
    CLAIMED("W0605", "该一卡通已被认领"),
    /**
     * code: 0000
     * explanation: ok
     * 含义：请求处理完成，正常返回
     */
    OK("0000", "ok");


    /**
     * 状态码
     */
    private String code;
    /**
     * 解释 code 的含义
     */
    private String explanation;

    ResultCodeEnum(String code, String explanation) {
        this.code = code;
        this.explanation = explanation;
    }
}
