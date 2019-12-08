package com.baizhi.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.entity.DemoData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEasyExcel {
    String fileName = "F:\\xls\\" + new Date().getTime() + "DemoData.xlsx";

    @Test
    public void test01() {
        //写法一链式调用
        //EasyExcel.write(fileName,DemoData.class).sheet("信息").doWrite(data());
        //写法2
        ExcelWriter build = EasyExcel.write(fileName, DemoData.class).build();
        //String : 页名称  Int: 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("测试页").build();
        build.write(data(), sheet);
        build.finish();
    }

    private List<DemoData> data() {
        DemoData demoData1 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData2 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData3 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData4 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        List<DemoData> demoData = Arrays.asList(demoData1, demoData2, demoData3, demoData4);
        return demoData;
    }
}
