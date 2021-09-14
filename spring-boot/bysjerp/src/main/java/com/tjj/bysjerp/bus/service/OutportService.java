package com.tjj.bysjerp.bus.service;

import com.tjj.bysjerp.bus.domain.Outport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author owen xu
 * @since 2020-04-18
 */
public interface OutportService extends IService<Outport> {

    /**
     * 退货方法
     * @param id 进货ID
     * @param number 退货数量
     * @param remark 退货备注(原因)
     */
    void addOutPort(Integer id, Integer number, String remark);
}
