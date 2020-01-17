package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.ElIcon;
import com.winston.entity.ElIconExample;
import com.winston.exception.ErrorException;
import com.winston.mapper.ElIconMapper;
import com.winston.redis.RedisService;
import com.winston.redis.key.IconKey;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IElIconService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ElIconServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/8 11:38
 * @Version：
 */
@Service("elIconServiceBase")
public class ElIconServiceBaseImpl implements IElIconService {

    @Autowired
    private ElIconMapper mapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<ElIcon> queryAll() {
        return mapper.selectByExample(new ElIconExample());
    }

    @Override
    public Result query(ElIcon elIcon, Integer page, Integer length) {
        PageHelper.startPage(page, length);

        ElIconExample example = new ElIconExample();
        ElIconExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(elIcon.getIconClass())){
            criteria.andIconClassLike("%"+elIcon.getIconClass()+"%");
        }
        List<ElIcon> elIcons = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(elIcons);
        return Result.success(elIcons, pageInfo.getTotal());
    }

    @Override
    public ElIcon queryById(Integer id) {
        ElIcon redisData = redisService.get(IconKey.ICON_ID_KEY, id + "", ElIcon.class);
        if(redisData != null){
            return redisData;
        }
        ElIcon elIcon = mapper.selectByPrimaryKey(id);
        redisService.set(IconKey.ICON_ID_KEY, id+"", elIcon);
        return elIcon;
    }

    @Override
    public void add(ElIcon elIcon) {
        ElIconExample example = new ElIconExample();
        example.createCriteria().andIconClassEqualTo(elIcon.getIconClass());
        List<ElIcon> list = mapper.selectByExample(example);
        if(list != null && list.size()>0){
            throw new ErrorException(CodeMsg.ICON_ALREADY_EXIST);
        }
        mapper.insertSelective(elIcon);
    }

    @Override
    public void update(ElIcon elIcon) {
        mapper.updateByPrimaryKeySelective(elIcon);
        redisService.del(IconKey.ICON_ID_KEY, elIcon.getId()+"");
    }

    @Override
    public void del(Integer id) {
        mapper.deleteByPrimaryKey(id);
        redisService.del(IconKey.ICON_ID_KEY, id+"");
    }
}
