package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface CourierService {
    /**
     * 保存或更新快递员信息
     *
     * @param courier
     */
    void save(Courier courier);

    /**
     * 删除快递员信息
     *
     * @param couriers
     */
    void delete(Courier[] couriers);

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    Page<Courier> findPageData(Specification specification, PageRequest pageRequest);
}
