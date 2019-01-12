package cn.itcast.bos.controller;


import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/area")
@Transactional
public class AreaController {
    @Autowired
    private AreaService areaService;

    //分页查询
    @RequestMapping("/pageQuery")
    public Map pageQuery(int page, int rows, Area area) {
        PageRequest pageRequest = PageRequest.of(page - 1, rows);

        //创造查询条件
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(area.getProvince())) {
                    Predicate predicate = criteriaBuilder.like(
                            root.get("province").as(String.class),
                            "%" + area.getProvince() + "%");
                    list.add(predicate);
                }
                if (StringUtils.isNotBlank(area.getCity())) {
                    Predicate predicate = criteriaBuilder.like(
                            root.get("city").as(String.class),
                            "%" + area.getCity() + "%");
                    list.add(predicate);
                }
                if (StringUtils.isNotBlank(area.getDistrict())) {
                    Predicate predicate = criteriaBuilder.like(
                            root.get("district").as(String.class),
                            "%" + area.getDistrict() + "%");
                    list.add(predicate);
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<Area> areas = areaService.findPageData(specification, pageRequest);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("total", areas.getTotalElements());
        map.put("rows", areas.getContent());
        return map;
    }

    @RequestMapping("/save")
    public ResponseResult save(Area area) {
        try {
            areaService.save(area);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }

    //批量区域数据导入
    @RequestMapping("/batchImport")
    public void batchImport(MultipartFile file) throws IOException {
        ArrayList<Area> areas = new ArrayList<>();
        //编写解析代码逻辑
        //基于.xls格式解析HSSF
        //1.加载Excel文件对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
        //2.读取一个sheet
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //3.读取sheet每一行
        for (Row row : sheet) {
            //一行数据 对应 一个区域对象
            if (row.getRowNum() == 0) {
                //第一行 跳过
                continue;
            }
            //跳过空行
            if (row.getCell(0) == null ||
                    StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                continue;
            }

            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            //pinyin处理
            //简码处理
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer stringBuffer = new StringBuffer();
            for (String head : headArray) {
                stringBuffer.append(head);
            }

            String shortcode = stringBuffer.toString();
            area.setShortcode(shortcode);

            //城市编码
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citycode);

            areas.add(area);
        }
        //调用业务层对象
        areaService.saveBatch(areas);
    }


    @RequestMapping("/delete")
    public ResponseResult delete(@RequestBody Area[] deleteRows) {
        try {
            areaService.delete(deleteRows);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }
}
