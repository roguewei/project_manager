package com.winston.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.GroupUserRole;
import com.winston.entity.Picture;
import com.winston.entity.User;
import com.winston.entity.UserExample;
import com.winston.exception.ErrorException;
import com.winston.jwt.comment.RawAccessJwtToken;
import com.winston.mapper.UserMapper;
import com.winston.redis.RedisService;
import com.winston.redis.key.UserKey;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IGroupUserRoleService;
import com.winston.service.IPictureService;
import com.winston.service.IUserService;
import com.winston.utils.PasswordHelper;
import com.winston.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Winston
 * @title: UserServiceImpl
 * @projectName shiroDemo
 * @description: 基本方法 没必要就尽量不改动这里的代码，而使用继承子类去扩展
 * @date 2019/7/24 14:25
 */
@Service("userServiceBase")
public class UserServiceBaseImpl implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RawAccessJwtToken rawAccessJwtToken;

    @Autowired
    private IPictureService pictureServiceBase;

    @Autowired
    private IGroupUserRoleService groupUserRoleServiceBase;

    @Autowired
    private RedisService redisService;

    @Override
    public Long queryCount() {
        return mapper.countByExample(new UserExample());
    }

    @Override
    public List<User> queryAll() {
        return mapper.selectByExample(new UserExample());
    }

    @Override
    public Result queryByUser(User user, int page, int length) {
        PageHelper.startPage(page, length);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if(user.getId() != null){
            criteria.andIdEqualTo(user.getId());
        }
        if(StringUtils.isNotBlank(user.getUsername())) {
            criteria.andUsernameLike("%"+user.getUsername()+"%");
        }
        List<User> users = mapper.selectByExample(example);
        users.forEach(item -> {
            item.setPassword("");
        });
        PageInfo pageInfo = new PageInfo(users);
        return Result.success(users, pageInfo.getTotal());
    }

    @Override
    public User queryById(Integer id) {
        User redisData = redisService.get(UserKey.USER_ID_KEY, id + "", User.class);
        if(redisData == null){
            User user = mapper.selectByPrimaryKey(id);
            user.setPassword("");
            redisService.set(UserKey.USER_ID_KEY, id+"", user);
            return user;
        }
        return redisData;
    }

    @Override
    public List<User> queryByIds(List<Integer> ids) {
        List<User> users = new ArrayList<>();
        if(ids != null && ids.size() > 0){
            UserExample example = new UserExample();
            example.createCriteria().andIdIn(ids);
            users = mapper.selectByExample(example);
        }
        return users;
    }

    @Override
    public User queryByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = mapper.selectByExample(example);
        if(users != null && users.size() > 0){
            User user = users.get(0);
            if(user.getHead() != null){
                Picture picture = pictureServiceBase.queryById(user.getHead());
                user.setHeadpath(picture.getUrl());
            }
            return user;
        }
        return null;
    }

    @Override
    public Result queryByGroupId(Integer groupId, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        List<User> resultList = new ArrayList<>();
        List<GroupUserRole> groupUserRoles = groupUserRoleServiceBase.queryByGroupId(groupId);
        if(groupUserRoles != null && groupUserRoles.size() > 0){
            List<Integer> ids = new ArrayList<>();
            groupUserRoles.forEach(item -> {
                ids.add(item.getUserId());
            });
            resultList = queryByIds(ids);
        }
        PageInfo pageInfo = new PageInfo(groupUserRoles);
        return Result.success(resultList, pageInfo.getTotal());
    }

    @Override
    public int save(User user) {
        User exist = queryByUsername(user.getUsername());
        if(exist != null){
            throw new ErrorException(CodeMsg.USER_ALREADY_EXIST);
        }
        String userName = rawAccessJwtToken.getUserName();
        long nowTime = new Date().getTime();
        String salt = StringUtil.getRandomString(5);

        user.setEnable("1");
        user.setState("1");
        user.setCreateOpr(userName);
        user.setCreateTime(nowTime);
        user.setUpdateOpr(userName);
        user.setUpdateTime(nowTime);
        user.setOperatorType("0");
        user.setSalt(salt);
        user.setPassword(passwordHelper.encryptPasswordSalt(user.getPassword(), salt));
        mapper.insertSelective(user);
        return user.getId();
    }

    @Override
    public void update(User user) {
        if(user == null || user.getId() == null){
            throw new ErrorException(CodeMsg.USER_UPDATE_FAILED);
        }
        user.setPassword(null);
        int i = mapper.updateByPrimaryKeySelective(user);
        if(i <= 0){
            throw new ErrorException(CodeMsg.USER_UPDATE_FAILED);
        }
    }

    @Override
    public void updatePwd(User user) {
        if(user == null || user.getId() == null){
            throw new ErrorException(CodeMsg.USER_UPDATE_FAILED);
        }
        String salt = StringUtil.getRandomString(5);
        user.setSalt(salt);
        user.setPassword(passwordHelper.encryptPasswordSalt(user.getPassword(), salt));
        mapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(Integer id) {
        User user = mapper.selectByPrimaryKey(id);
        if(user != null){
            user.setState("0");
            mapper.updateByPrimaryKeySelective(user);
        }else{
            throw new ErrorException(CodeMsg.USER_DEL_FAILE);
        }
    }

    @Override
    public User queryByOpenId(String openId) {
//        UserExample example = new UserExample();
//        example.createCriteria().andOpenIdEqualTo(openId);
//        List<User> users = mapper.selectByExample(example);
//        return users.get(0);
        return null;
    }
}
