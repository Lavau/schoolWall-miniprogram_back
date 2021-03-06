package top.leeti.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.leeti.config.manager.WeChatSessionManager;
import top.leeti.realm.MyRealm;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        return matcher;
    }

    @Bean
    public Realm realm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        return new MyRealm();
    }

    @Bean
    public WeChatSessionManager sessionManager() {
        WeChatSessionManager weChatSessionManager = new WeChatSessionManager();
        weChatSessionManager.setGlobalSessionTimeout(60 * 60 * 24);
        return weChatSessionManager;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("realm") Realm realm,
                           @Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // ????????????????????? url????????????????????????????????????
        shiroFilterFactoryBean.setLoginUrl("/miniprogram/noLogin/error");
        // ?????????????????????????????????????????? url
//        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        // ???????????????????????????????????? url
        shiroFilterFactoryBean.setUnauthorizedUrl("/app/noLogin/noAccess");

        /*
         * anon????????????????????????
         * authc????????????????????????
         * user?????????rememberMe?????????
         * perms????????????????????????
         * roles[?????????]??????????????????????????????
         */
        //??????????????? url ?????????
        Map<String, String> filterChain = new HashMap<>(5);
        filterChain.put("/miniprogram/login/**", "authc");
        filterChain.put("/miniprogram/noLogin/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);

        return shiroFilterFactoryBean;
    }
}
