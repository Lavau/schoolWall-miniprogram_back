package top.leeti.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.leeti.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@RestController
public class PictureController {

    @RequestMapping("/miniprogram/picture/obtain")
    public void sendPictureToClient(int typeId, String uuid, String fileName, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream writer = null;
        try {
            inputStream = new FileInputStream(new File(
                    FileUtil.getRootDirectory(typeId, uuid), fileName));
            writer = response.getOutputStream();

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
