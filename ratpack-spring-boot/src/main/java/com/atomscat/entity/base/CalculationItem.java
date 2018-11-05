package com.atomscat.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "calculation_item")
public class CalculationItem implements Serializable {

    //@ApiModelProperty
    @Column(name = "id")
    @Id
    private String id;
    //@ApiModelProperty
    @Column(name = "name")
    private String name;
    //@ApiModelProperty(value = "单价")
    @Column(name = "price")
    private long price;
    //@ApiModelProperty(value = "数量范围，最小")
    @Column(name = "num_min")
    private long numMin;
    //@ApiModelProperty(value = "数量范围，最大，-1不限制")
    @Column(name = "num_max")
    private long numMax;
    //@ApiModelProperty(value = "分类id")
    @Column(name = "type_id")
    private String typeId;
    //@ApiModelProperty(value = "分类名称")
    @Column(name = "type_name")
    private String typeName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }


    public long getNumMin() {
        return numMin;
    }

    public void setNumMin(long numMin) {
        this.numMin = numMin;
    }


    public long getNumMax() {
        return numMax;
    }

    public void setNumMax(long numMax) {
        this.numMax = numMax;
    }


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
