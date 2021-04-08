package top.leeti.config.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义session管理器
 * 继承DefaultWebSessionManager,重写getSessionId方法
 */
@Slf4j
public class WeChatSessionManager extends DefaultWebSessionManager {

    public final static String HEADER_TOKEN_NAME = "JSessionId";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 逻辑:
     *     如果请求头中有 JSessionId，就分析它；
     *     没有就调用父类的方法
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        String JSessionId = WebUtils.toHttp(request).getHeader(HEADER_TOKEN_NAME);

        if(JSessionId == null) {
            return super.getSessionId(request, response);
        } else {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, JSessionId);
            log.info("JSessionId: {}", JSessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return JSessionId;
        }
    }

}
