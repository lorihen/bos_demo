package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courier")
@Transactional
public class CourierController {
    @Autowired
    private CourierService courierService;

    @RequestMapping("/save")
    public ResponseResult save(Courier courier){
        try {
            courierService.save(courier);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }

    @RequestMapping("/pageQuery")
    public Map pageQuery(int page,int rows, Courier courier){
        PageRequest pageRequest = PageRequest.of(page - 1, rows);

        //创建查询条件
        Specification<Courier> specification = new Specification<Courier>() {
            //Root用于获取属性字段,criteriaQuery可以用于表达式条件查询,criteriaBuilder用于面向对象条件查询
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?>
                    criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //对查询条件进行非空判断并逐一添加
                if (StringUtils.isNotBlank(courier.getCourierNum())) {
                    Predicate predicate = criteriaBuilder.equal(
                            root.get("courierNum").as(String.class),
                            courier.getCourierNum());
                    list.add(predicate);
                }
                if (StringUtils.isNotBlank(courier.getCompany())) {
                    Predicate predicate = criteriaBuilder.like(
                            root.get("company").as(String.class),
                            "%"+courier.getCompany()+"%");
                    list.add(predicate);
                }
                if (StringUtils.isNotBlank(courier.getType())) {
                    Predicate predicate = criteriaBuilder.equal(
                            root.get("type").as(String.class),
                            courier.getType());
                    list.add(predicate);
                }

                //多表查询
                Join<Courier, Standard> standardJoin = root.join("standard", JoinType.INNER);
                if (courier.getStandard() != null &&
                        StringUtils.isNotBlank(courier.getStandard().getName())){
                    Predicate predicate = criteriaBuilder.like(
                            standardJoin.get("name").as(String.class),
                            "%"+courier.getStandard().getName()+"%");
                    list.add(predicate);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<Courier> couriers = courierService.findPageData(specification,pageRequest);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("total",couriers.getTotalElements());
        map.put("rows",couriers.getContent());
        return map;
    }

    @RequestMapping("/deleteTag")
    public ResponseResult deleteTag(@RequestBody Courier[] deleteRows){
        try {
            for (Courier courier : deleteRows) {
                courier.setDeltag('1');
                courierService.save(courier);
            }
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }
}
