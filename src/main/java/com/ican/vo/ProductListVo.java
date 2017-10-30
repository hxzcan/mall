package com.ican.vo;

import java.math.BigDecimal;

/**
 * 商品
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/30 0030
 */
public class ProductListVo {
    private Integer id;
    private Integer categoryotId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private BigDecimal price;

    private Integer status;

    private String imageHost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryotId() {
        return categoryotId;
    }

    public void setCategoryotId(Integer categoryotId) {
        this.categoryotId = categoryotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    @Override
    public String toString() {
        return "ProductListVo{" +
                "id=" + id +
                ", categoryotId=" + categoryotId +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", subImages='" + subImages + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", imageHost='" + imageHost + '\'' +
                '}';
    }
}
