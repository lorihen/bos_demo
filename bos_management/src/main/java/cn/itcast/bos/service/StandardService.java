package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StandardService {
    /**
     * 保存收派标准
     * @param standard
     */
    void save(Standard standard);

    /**
     * 删除收派标准
     * @param standards
     */
    void delete(Standard[] standards);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    Page<Standard> findPageData(PageRequest pageRequest);

    /**
     * 查询全部
     * @return
     */
    List<Standard> findAll();
}
