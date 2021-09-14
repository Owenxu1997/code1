package com.tjj.bysjerp.sys.service.impl;

import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.mapper.DepartmentMapper;
import com.tjj.bysjerp.sys.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen
 * @since 2020-04-09
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {


    @Override
    public Department getById(Serializable id) {
        return super.getById(id);
    }


    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(Department entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean save(Department entity) {
        return super.save(entity);
    }
}
