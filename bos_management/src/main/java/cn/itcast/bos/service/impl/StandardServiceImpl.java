package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

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
    public void delete( Standard[] standards) {
        standardDao.deleteAll(Arrays.asList(standards));
    }

    @Override
    public Page<Standard> findPageData(PageRequest pageRequest) {
        return standardDao.findAll(pageRequest);
    }

    @Override
    public List<Standard> findAll() {
        return standardDao.findAll();
    }
}
