package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CourierService {
    /**
     * 保存收派标准
     * @param courier
     */
    void save(Courier courier);

    /**
     * 删除收派标准
     * @param couriers
     */
    void delete(Courier[] couriers);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    Page<Courier> findPageData(PageRequest pageRequest);
}
