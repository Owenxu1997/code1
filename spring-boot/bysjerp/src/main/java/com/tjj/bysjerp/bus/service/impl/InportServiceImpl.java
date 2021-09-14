package com.tjj.bysjerp.bus.service.impl;

import com.tjj.bysjerp.bus.domain.Goods;
import com.tjj.bysjerp.bus.domain.Inport;
import com.tjj.bysjerp.bus.mapper.GoodsMapper;
import com.tjj.bysjerp.bus.mapper.InportMapper;
import com.tjj.bysjerp.bus.service.GoodsService;
import com.tjj.bysjerp.bus.service.InportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {

    @Autowired
    private GoodsMapper goodsMapper;  //用mapper缓存不会变

    @Override
    public boolean save(Inport entity) {

        //根据商品编号查询商品--进货添加管理
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber()+entity.getNumber());
        goodsMapper.updateById(goods);
        //保存进货信息
        return super.save(entity);
    }

    /**
     * 假设一开始是500，后来加500 = 1000 ，修改的话应采取1000-500的做法恢复之前状态在进行添加赋值
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(Inport entity) {
        //根据进货Id查进货信息
        Inport inport = this.baseMapper.selectById(entity.getId());
        //根据商品Id查商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        //库存算法 当前库存-进货单修改之前的数值+修改后的数量
        goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());
        this.goodsMapper.updateById(goods);
        //更新进货单
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据进货Id查进货信息
        Inport inport = this.baseMapper.selectById(id);
        //根据商品Id查商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        //库存算法 当前库存-进货单数量
        goods.setNumber(goods.getNumber()-inport.getNumber());
        this.goodsMapper.updateById(goods);
        return super.removeById(id);
    }
}
