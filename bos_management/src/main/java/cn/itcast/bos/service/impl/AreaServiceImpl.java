package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public void saveBatch(List<Area> areas) {
        areaDao.saveAll(areas);
    }

    @Override
    public Page<Area> findPageData(Specification<Area> specification, PageRequest pageRequest) {
        return areaDao.findAll(specification,pageRequest);
    }

    @Override
    public void save(Area area) {
        if (area != null) {
            areaDao.save(area);
        }else{
            new RuntimeException("数据错误");
        }
    }

    @Override
    public void delete(Area[] areas) {
        areaDao.deleteAll(Arrays.asList(areas));
    }
}
