package com.winston.service;

import com.winston.entity.Groups;
import com.winston.entity.Permission;
import com.winston.entity.Role;
import com.winston.entity.User;
import com.winston.exception.ErrorException;
import com.winston.jwt.TokenService;
import com.winston.redis.RedisService;
import com.winston.redis.key.PermissionKey;
import com.winston.redis.key.SessionRedisKey;
import com.winston.result.CodeMsg;
import com.winston.service.IUserService;
import com.winston.utils.HttpUtil;
import com.winston.utils.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoginServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/2 14:34
 * @Version：
 */
@Service
public class LoginService {

    @Autowired
    private IUserService userServiceBase;

    @Autowired
    private IRoleService roleServiceBase;

    @Autowired
    private IGroupsService groupsServiceBase;

    @Autowired
    private IPermissionService permissionServiceBase;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    public Map<String, Object> login(User user){
        User query = userServiceBase.queryByUsername(user.getUsername());
        if(query == null){
            throw new ErrorException(CodeMsg.ACCOUNT_NOT_EXIST);
        }
        String encryptPassword = passwordHelper.encryptPasswordSalt(user.getPassword(), query.getSalt());
        if(!query.getPassword().equals(encryptPassword)){
            throw new ErrorException(CodeMsg.USERNAME_OR_PASSWORD_ERROR);
        }
        HttpServletRequest request = HttpUtil.getRequest();
        String sessionId = request.getSession().getId();
        redisService.set(SessionRedisKey.SESSION_KEY, sessionId, query);

        Role role = roleServiceBase.queryByUserId(query.getId());
        List<Groups> groups = groupsServiceBase.queryByUserId(query.getId());
        String token = tokenService.getToken(query.getId(), query.getUsername());

        // 存储用户权限
        putUserPer(query.getUsername());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        query.setPassword("");
        result.put("userInfo", query);
        result.put("role", role);
        if(groups != null && groups.size()> 0){
            result.put("group", groups.get(0));
        }

        return result;
    }

    public void logout(){
        tokenService.clearToken();
    }

    private void putUserPer(String userName){
        List<Permission> permissions = permissionServiceBase.queryByUserName(userName);
        if(permissions != null && permissions.size()>0){
            List<String> perUrl = new ArrayList<>();
            for(Permission permission : permissions){
                perUrl.add(permission.getPerurl());
            }
            String perUrlStr = String.join(",", perUrl);
            System.out.println(perUrlStr);
            HttpServletRequest request = HttpUtil.getRequest();
            String sessionId = request.getSession().getId();
            redisService.set(PermissionKey.PERMISSION_USER_KEY, sessionId, perUrlStr);
        }
    }

}
