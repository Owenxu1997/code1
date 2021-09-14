package com.tjj.bysjerp.bus.mapper;

import com.tjj.bysjerp.bus.domain.Apilist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Owen Xu
 * @since 2020-04-23
 */

@Repository
public interface ApilistMapper extends BaseMapper<Apilist> {


    List<Apilist> getAllApi();

    List<Apilist> getAllApiReviewed();

    List<Apilist> getTest();

    List<Apilist> getReview();

    List<Apilist> getPersonalApi(int userId);

    List<Apilist> getPersonalReview(int userId);

    List<Apilist> getPersonalReviewed(int userId);

    List<Apilist> getPersonalTest(int userId);

    List<Apilist> getPersonalTested(int userId);

    int addApi(Apilist api);

//    int deleteById(Apilist api);

    Apilist getApiById(int id);

    int reviewPassed(int id);

    int testPassed(int id);

}
