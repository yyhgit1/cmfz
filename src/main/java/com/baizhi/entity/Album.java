package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album implements Serializable {
    @Id
    private String id;
    private String title;
    private String score;
    private String author;
    private String broadcast;
    private Integer count;
    private String description;
    private String status;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
    private String cover;
    @Transient
    private List<Chapter> chapters;
}
