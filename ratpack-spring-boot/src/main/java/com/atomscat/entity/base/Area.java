package com.atomscat.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "area")
public class Area implements Serializable {

    //@ApiModelProperty(value = "null")
    @Column(name = "id")
    @Id
    private long id;
    //@ApiModelProperty(value = "null")
    @Column(name = "name")
    private String name;
    //@ApiModelProperty(value = "null")
    @Column(name = "area_id")
    private String areaId;
    //@ApiModelProperty(value = "null")
    @Column(name = "city_id")
    private String cityId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
