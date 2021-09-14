package com.tjj.bysjerp.sys.service.impl;

import com.tjj.bysjerp.sys.domain.Loginfo;
import com.tjj.bysjerp.sys.mapper.LoginfoMapper;
import com.tjj.bysjerp.sys.service.LoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen
 * @since 2020-04-08
 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

}
