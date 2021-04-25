package top.leeti.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Slf4j
public class WechatUtil {

//    private static final String APP_ID = "wxf4eeba633c89a51f";
//    private static final String APP_SECRET = "8a7de97a3189c29e3faf87275e3449ee";
    private static final String APP_ID = "wx4178db6fa98e73de";
    private static final String APP_SECRET = "8abc434a7832d72dfd570b62f50e3e8f";
    private static final String GRANT_TYPE = "authorization_code";

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * 像微信服务器发送请求，获取 sessionKey、openId
     * @param code
     *        本次登录请求的小程序传来的标识（保证传来的 code 绝不为空或是空字符串）
     * @return
     *        Map key:sessionKey、openId。如果不能正确地获取到 sessionKey、openId，
     *        返回的 map 中，值为 null。
     */
    public static Map<String, String> acquireSessionKeyAndOpenId(String code) {
        log.info("当前使用的code: {}", code);

        Map<String, String> map = new Hashtable<>();
        try{
            String url = String.format("https://api.weixin.qq.com/sns/jscode2session" +
                    "?appid=%s&secret=%s&js_code=%s&grant_type=%s", APP_ID, APP_SECRET, code, GRANT_TYPE);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String[] results = response.getBody().split("\"");
            map.put("sessionKey", results[4]);
            map.put("openId", results[7]);
        } catch (Exception e){
            map.put("sessionKey", null);
            map.put("openId", null);
        }
        return map;
    }

    /**
     * 验证图片是否含有敏感信息
     *      如果含有敏感信息或验证过程中出错，返回 true
     *      否则，返回 false
     */
    public static Boolean verifyPicture(Integer typeId, String id, String fileName) throws IOException {
        String accessToken = obtainAccessToken();
        String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        File file = new File(FileUtil.getRootDirectory(typeId, id), fileName);

        HttpHeaders pictureHeader = new HttpHeaders();
        pictureHeader.setContentType(MediaType.IMAGE_JPEG);
        pictureHeader.setContentDispositionFormData("file", fileName);
        ByteArrayResource bar = new ByteArrayResource(obtainByteArrayOfFile(file));
        HttpEntity<ByteArrayResource> picturePart = new HttpEntity<>(bar, pictureHeader);

        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        multipartRequest.add("media", picturePart);

        HttpEntity entity = new HttpEntity(multipartRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        Map<String, Object> res = (Map<String, Object>)JSON.parse(response.getBody());

        log.info("图片验证：{}", res);

        Integer errcode = res != null ? (Integer) res.getOrDefault("errcode", null) : null;

        return errcode == null || errcode.equals(87014);
    }

    private static byte[] obtainByteArrayOfFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        List<Byte> bytes = new ArrayList<>();
        int b = 0;
        while ((b = inputStream.read()) != -1) {
            bytes.add((byte)b);
        }
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }

    private static String obtainAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + APP_SECRET;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Map<String, String> res = (Map<String, String>)JSON.parse(response.getBody());
        return res != null ? res.getOrDefault("access_token", null) : null;
    }
}
