package cn.itcast.bos.controller;


import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/area")
@Transactional
public class AreaController {
    @Autowired
    private AreaService areaService;

    //批量区域数据导入
    @RequestMapping("/batchImport")
    public void batchImport(MultipartFile file) throws IOException{
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
            if(row.getRowNum() == 0){
                //第一行 跳过
                continue;
            }
            //跳过空行
            if(row.getCell(0)==null||
                    StringUtils.isBlank(row.getCell(0).getStringCellValue())){
               continue;
            }

            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());

            areas.add(area);
        }
        //调用业务层对象
        areaService.saveBatch(areas);
    }
}
