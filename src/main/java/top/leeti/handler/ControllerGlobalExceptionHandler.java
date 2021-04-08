package top.leeti.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.leeti.entity.result.Result;
import top.leeti.exception.RecordOfDataBaseNoFoundException;

@Slf4j
@RestControllerAdvice
public class ControllerGlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String dealWithException(Exception e, HttpRequest request) {
        log.info("error uri: {}", request.getURI());
        e.printStackTrace();
        Result<Boolean> result = new Result<>();
        result.setSuccess(false);
        result.setMsg("服务器繁忙");
        return JSON.toJSONString(result);
    }

    @ExceptionHandler(RecordOfDataBaseNoFoundException.class)
    public String dealWithRecordOfDataBaseNoFoundException(Exception e) {
        e.printStackTrace();

        log.info("{}", e.getMessage());

        Result<Boolean> result = new Result<>();
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        return JSON.toJSONString(result);
    }
}
