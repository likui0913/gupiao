package com.gupiao.service;

import com.gupiao.util.StaticValue;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理类
 */
public class PicService {


    /**
     * 创建图片
     *
     * @param name
     */
    public void createPic(String name,DefaultCategoryDataset mDataset){

        /* 构造数据Demo
            DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
            mDataset.addValue(1, "First", "2013");
            mDataset.addValue(3, "First", "2014");
            mDataset.addValue(2, "First", "2015");
            mDataset.addValue(6, "First", "2016");
            mDataset.addValue(5, "First", "2017");
            mDataset.addValue(12, "First", "2018");
            mDataset.addValue(14, "Second", "2013");
            mDataset.addValue(13, "Second", "2014");
            mDataset.addValue(12, "Second", "2015");
            mDataset.addValue(9, "Second", "2016");
            mDataset.addValue(5, "Second", "2017");
            mDataset.addValue(7, "Second", "2018");
        */
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        JFreeChart mChart = ChartFactory.createLineChart(
                name,//图名字
                "日期",//横坐标
                "价格",//纵坐标
                mDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                true, // 采用标准生成器
                false);// 是否生成超链接
        try {
            ChartUtils.saveChartAsJPEG(new File(StaticValue.PIC_PATH_SETTING + name),mChart,800,400);
        } catch (IOException e) {
            throw new RuntimeException("图片[" + name + "] 生成失败。" + e.getMessage());
        }


    }

    /**
     * 读取一个图片
     *
     * @param picName
     */
    public void getPic(String picName){}

    /**
     * 删除一个图片
     * @param picName
     */
    public void deletePic(String picName){}

    /**
     * 清除全部图片
     */
    public void clearCacheAllPic(){}

}
