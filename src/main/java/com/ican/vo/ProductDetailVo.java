package com.ican.vo;

import com.ican.pojo.Product;


/**
 * 对product pojo进一步封装
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/27 0027
 * Time: 17:47
 */
public class ProductDetailVo extends Product{


    private String imageHost;//图片的服务器访问前缀

    private Integer parentCategoryId;//父分类

    private String creatDate;//创建时间

    private String updateDate;//更新时间

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "ProductDetailVo{" +
                "imageHost='" + imageHost + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                ", creatDate='" + creatDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
