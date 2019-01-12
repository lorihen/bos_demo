package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AreaDao extends JpaRepository<Area,String> ,JpaSpecificationExecutor<Area> {
}
