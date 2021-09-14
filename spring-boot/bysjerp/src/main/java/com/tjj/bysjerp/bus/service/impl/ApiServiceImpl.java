package com.tjj.bysjerp.bus.service.impl;

import com.tjj.bysjerp.bus.domain.Api;
import com.tjj.bysjerp.bus.mapper.ApiMapper;
import com.tjj.bysjerp.bus.service.ApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen Xu
 * @since 2020-04-23
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

}
