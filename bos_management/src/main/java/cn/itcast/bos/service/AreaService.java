package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    /**
     * 读取保存上传文件的信息
     * @param areas
     */
    void saveBatch(List<Area> areas);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    Page<Area> findPageData(Specification<Area> specification, PageRequest pageRequest);

    /**
     * 保存更新区域记录
     */
    void save(Area area);

    /**
     * 删除区域信息记录
     * @param areas
     */
    void  delete(Area[] areas);
}
