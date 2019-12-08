package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;

import java.net.URL;

@Data
@ContentRowHeight(100)
@ColumnWidth(100 / 6)
public class Banner1 extends Banner {
    @ExcelProperty("图片")
    private URL ur;
}
