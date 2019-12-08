package com.baizhi.overrid;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String[] split = value.split("/");
        // 获取图片名字
        String imageName = split[split.length - 1];
        // 获得绝对路径
        String url = "F:\\IdeaProjects\\hqxm\\cmfz\\src\\main\\webapp\\back\\upload\\img\\" + imageName;
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }
}
