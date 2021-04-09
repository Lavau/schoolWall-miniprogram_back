package top.leeti.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.leeti.entity.result.Result;
import top.leeti.exception.RecordOfDataBaseNoFoundException;

@Slf4j
@RestControllerAdvice
public class ControllerGlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String dealWithException(Exception e) {
        e.printStackTrace();
        Result<Boolean> result = new Result<>();
        result.setSuccess(false);
        result.setMsg("服务器繁忙");
        return JSON.toJSONString(result);
    }

    @ExceptionHandler(RecordOfDataBaseNoFoundException.class)
    public String dealWithRecordOfDataBaseNoFoundException(RecordOfDataBaseNoFoundException e) {
        log.info("提示的信息:{}, id:{}, uriOrMethod:{}", e.getMessage(), e.getId(),e.getUriOrMethod());
        Result<Boolean> result = new Result<>();
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        return JSON.toJSONString(result);
    }
}
