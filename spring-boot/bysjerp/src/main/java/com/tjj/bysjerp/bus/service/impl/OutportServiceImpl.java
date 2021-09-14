package com.tjj.bysjerp.bus.service.impl;

import com.tjj.bysjerp.bus.domain.Goods;
import com.tjj.bysjerp.bus.domain.Inport;
import com.tjj.bysjerp.bus.domain.Outport;
import com.tjj.bysjerp.bus.mapper.GoodsMapper;
import com.tjj.bysjerp.bus.mapper.InportMapper;
import com.tjj.bysjerp.bus.mapper.OutportMapper;
import com.tjj.bysjerp.bus.service.OutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjj.bysjerp.sys.common.WebUtils;
import com.tjj.bysjerp.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen Xu
 * @since 2020-04-18
 */
@Service
@Transactional
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {

    @Autowired
    private InportMapper inportMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addOutPort(Integer id, Integer number, String remark) {
        //1.根据进货ID查询进货信息
        Inport inport = this.inportMapper.selectById(id);
        //2.根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        this.goodsMapper.updateById(goods);
        //3.添加进货单信息
        Outport entity = new Outport();
        entity.setGoodsid(inport.getGoodsid());
        entity.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        entity.setOperator(user.getName());
        entity.setOutportprice(inport.getInportprice());
        entity.setOutporttime(new Date());
        entity.setPaytype(inport.getPaytype());
        entity.setProviderid(inport.getProviderid());
        entity.setRemark(remark);
        this.getBaseMapper().insert(entity);
    }
}
