package top.leeti.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Hashtable;
import java.util.Map;

@Slf4j
public class WechatUtil {

    private static final String APP_ID = "wxf4eeba633c89a51f";
    private static final String APP_SECRET = "8a7de97a3189c29e3faf87275e3449ee";
    private static final String GRANT_TYPE = "authorization_code";

    /**
     * 像微信服务器发送请求，获取 sessionKey、openId
     * @param code
     *        本次登录请求的小程序传来的标识（保证传来的 code 绝不为空或是空字符串）
     * @return
     *        Map key:sessionKey、openId。如果不能正确地获取到 sessionKey、openId，
     *        返回的 map 中，值为 null。
     */
    public static Map<String, String> acquireSessionKeyAndOpenId(String code){
        log.info("当前使用的code: {}", code);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new Hashtable<>();
        try{
            String url = String.format("https://api.weixin.qq.com/sns/jscode2session" +
                    "?appid=%s&secret=%s&js_code=%s&grant_type=%s", APP_ID, APP_SECRET, code, GRANT_TYPE);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            ResponseEntity<String> response = restTemplate.getForEntity( url, String.class);
            String[] results = response.getBody().split("\"");
            map.put("sessionKey", results[4]);
            map.put("openId", results[7]);
        } catch (Exception e){
            map.put("sessionKey", null);
            map.put("openId", null);
        }
        return map;
    }
}
