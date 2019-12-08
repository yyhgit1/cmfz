package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ContentRowHeight(100)
@ColumnWidth(100 / 6)
public class Banner implements Serializable {
    @Id
    @ExcelIgnore  //忽略
    private String id;
    @ExcelProperty("标题")
    private String title;//展示的图片名
    // 复杂方法 @ExcelProperty(converter = ImageConverter.class,value = "图片")
    //简单
    @ExcelIgnore
    private String url;//上传文件的路径
    @ExcelProperty("跳转路径")
    private String href;//要跳转的路径
    @Column(name = "create_date")
    // @JsonFormat(pattern = "yyyy-MM-dd",timezone ="GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    @ExcelProperty("创建时间")
    private Date date;
    @ExcelProperty("描述")
    private String description;
    @ExcelProperty("状态")
    private String status;

}
