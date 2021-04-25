package top.leeti.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @author 兮赫
 */
@Slf4j
public class SenInfoCheckUtil {
    /**
     * 图片违规检测,对外提供,直接使用
     *
     * @param accessToken
     * @param file
     * @return
     */
    public static Boolean imgFilter(String accessToken, MultipartFile file) {
        String contentType = file.getContentType();
        return checkPic(file, accessToken, contentType);
    }

    /**
     * 文本违规检测,对外提供,直接使用
     *
     * @param accessToken
     * @param content
     * @return
     */
    public static Boolean cotentFilter(String accessToken, String content) {
        return checkContent(accessToken, content);
    }

    /**
     * 文本违规检测,对外提供,直接使用
     *
     * @param content
     * @return
     */
    public static Boolean cotentFilter(String content) {
        return checkContent(getAccessToken(), content);
    }

    /**
     * 校验图片是否有敏感信息
     *
     * @param multipartFile
     * @return
     */
    private static Boolean checkPic(MultipartFile multipartFile, String accessToken, String contentType) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/octet-stream");
            InputStream inputStream = multipartFile.getInputStream();
            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            request.setEntity(new ByteArrayEntity(byt, ContentType.create(contentType)));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            log.info("敏感信息验证：{}", result);
            JSONObject jso = JSONObject.parseObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 校验内容是否有敏感信息
     * @param accessToken
     * @param content
     * @return
     */
    private static Boolean checkContent(String accessToken, String content) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/json");
            Map<String, String> map = new HashMap<>();
            map.put("content", content);
            String body = JSONObject.toJSONString(map);
            request.setEntity(new StringEntity(body, ContentType.create("text/json", "UTF-8")));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            log.info("敏感信息验证：{}", result);
            JSONObject jso = JSONObject.parseObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 返回状态信息,可以修改为自己的逻辑
     * @param jso
     * @return
     */
    private static Boolean getResult(JSONObject jso) {
        Object errcode = jso.get("errcode");
        int errCode = (int) errcode;
        if (errCode == 0) {
            return true;
        } else if (errCode == 87014) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 获取小程序的 access_token
     * @return
     */
    public static String getAccessToken() {
        AccessTokenVO accessTokenVO = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            //改成自己的appid和secret    private static final String APP_ID = "";
            //    private static final String APP_SECRET = "";
            HttpGet request = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx4178db6fa98e73de&secret=8abc434a7832d72dfd570b62f50e3e8f");
            request.addHeader("Content-Type", "application/json");
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            accessTokenVO = JSONObject.parseObject(result, AccessTokenVO.class);
            //返回token
            return accessTokenVO.getAccess_token();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    public static class AccessTokenVO {
        private String access_token;
        private Integer expires_in;
    }
}
