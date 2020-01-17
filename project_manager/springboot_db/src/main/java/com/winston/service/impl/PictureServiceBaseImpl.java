package com.winston.service.impl;

import com.winston.entity.Picture;
import com.winston.mapper.PictureMapper;
import com.winston.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PictureServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/19 17:14
 * @Version：
 */
@Service("pictureServiceBase")
public class PictureServiceBaseImpl implements IPictureService {

    @Autowired
    private PictureMapper mapper;

    @Override
    public Picture queryById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
}
