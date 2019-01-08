package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardDao standardDao;

    @Override
    public void save(Standard standard) {
        if (standard != null) {
            standardDao.save(standard);
        }else{
            new RuntimeException("数据错误");
        }
    }

    @Override
    public void delete(Standard[] standards) {
        for (Standard standard : standards) {
            standardDao.delete(standard);
        }
    }

    @Override
    public Page<Standard> findPageData(PageRequest pageRequest) {
        return standardDao.findAll(pageRequest);
    }
}
