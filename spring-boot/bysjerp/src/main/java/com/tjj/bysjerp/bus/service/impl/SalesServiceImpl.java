package com.tjj.bysjerp.bus.service.impl;

import com.tjj.bysjerp.bus.domain.Goods;
import com.tjj.bysjerp.bus.domain.Inport;
import com.tjj.bysjerp.bus.domain.Sales;
import com.tjj.bysjerp.bus.mapper.GoodsMapper;
import com.tjj.bysjerp.bus.mapper.SalesMapper;
import com.tjj.bysjerp.bus.service.SalesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen Xu
 * @since 2020-04-20
 */
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Sales entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber()-entity.getNumber());
        //更新库存
        goodsMapper.updateById(goods);
        return super.save(entity);
    }

    /**
     * 更新商品销售信息
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(Sales entity) {
        //根据销售单ID查询
        Sales sales = baseMapper.selectById(entity.getId());
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        //商品容量=原库存-销售修改前数量+修改后数量
        goods.setNumber(goods.getNumber()-entity.getNumber()+sales.getNumber());
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据进货Id查进货信息
        Sales sales = this.baseMapper.selectById(id);
        //根据商品Id查商品信息
        Goods goods = this.goodsMapper.selectById(sales.getGoodsid());
        //库存算法 当前库存-进货单数量
        goods.setNumber(goods.getNumber()-sales.getNumber());
        this.goodsMapper.updateById(goods);
        return super.removeById(id);
    }
}
