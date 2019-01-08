package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.CourierService;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    public Map pageQuery(int page,int rows){
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        Page<Courier> couriers = courierService.findPageData(pageRequest);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("total",couriers.getTotalElements());
        map.put("rows",couriers.getContent());
        return map;
    }

    @RequestMapping("/delete")
    public ResponseResult delete(@RequestBody Courier[] deleteRows){
        try {
            courierService.delete(deleteRows);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }
}
