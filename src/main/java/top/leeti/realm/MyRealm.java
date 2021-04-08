package top.leeti.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.leeti.entity.User;
import top.leeti.service.UserService;

import javax.annotation.Resource;

@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        User user = userService.getUserByOpenId(usernamePasswordToken.getUsername());

        if (user == null) {
            throw new AccountException();
        }

        log.info("该登录用户的openId:{}", usernamePasswordToken.getUsername());

        ByteSource saltOfCredential = ByteSource.Util.bytes(user.getStuId());
        return new SimpleAuthenticationInfo(user, String.valueOf(usernamePasswordToken.getPassword()),
                saltOfCredential, getName());
    }
}
