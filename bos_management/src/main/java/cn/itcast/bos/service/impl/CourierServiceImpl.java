package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierDao courierDao;

    @Override
    public void save(Courier courier) {
        if (courier != null) {
            courierDao.save(courier);
        }else{
            new RuntimeException("数据错误");
        }
    }

    @Override
    public void delete(Courier[] couriers) {
        for (Courier courier : couriers) {
            courierDao.delete(courier);
        }
    }

    @Override
    public Page<Courier> findPageData(PageRequest pageRequest) {
        return courierDao.findAll(pageRequest);
    }
}
