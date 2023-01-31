package com.gupiao.xjob;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.mapper.SysSettingMapper;
import com.gupiao.service.StockService;
import com.gupiao.util.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDateTime;

/**
 * 股票交易明细数据定时任务，暂时停止使用，使用UpdateFullStockUpdateTask 代替
 */
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling //2.开启定时任务
@Slf4j
public class UpdateStockTransactionDetailTask {

    @Autowired
    StockService stockService;

    @Autowired
    SysSettingMapper sysSettingMapper;

    //@Scheduled(cron="0/5 * * * * *")
    private void configureTasks(){



        log.info("UpdateStockTransactionDetailTask 定时任务启动");
        SysSetting setting = sysSettingMapper.selectByCode(StaticValue.UPDATE_STOCK_DATA_KEY_THREAD_SIZE);
        if(null == setting){
            setting = new SysSetting();
            setting.setSysKey(StaticValue.UPDATE_STOCK_DATA_KEY_THREAD_SIZE);
            setting.setSysValue("1");
            setting.setDes("");
            sysSettingMapper.insert(setting);
        }

        stockService.getAllStockYesterdayData(Integer.parseInt(setting.getSysValue()), Boolean.TRUE);

    }

}
