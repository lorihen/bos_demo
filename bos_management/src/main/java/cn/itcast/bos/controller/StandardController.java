package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/standard")
@Transactional
public class StandardController {
    @Autowired
    private StandardService standardService;

    @RequestMapping("/save")
    public ResponseResult save(Standard standard){
        try {
            standardService.save(standard);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }

    @RequestMapping("/pageQuery")
    public Map pageQuery(int page,int rows){
        PageRequest pageRequest = PageRequest.of(page - 1, rows);
        Page<Standard> standards = standardService.findPageData(pageRequest);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("total",standards.getTotalElements());
        map.put("rows",standards.getContent());
        return map;
    }

    @RequestMapping("/delete")
    public ResponseResult delete(@RequestBody Standard[] deleteRows){
        try {
            standardService.delete(deleteRows);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }
}
