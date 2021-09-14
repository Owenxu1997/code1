package com.tjj.bysjerp.bus.vo;

import com.tjj.bysjerp.bus.domain.Customer;
import com.tjj.bysjerp.bus.domain.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Owen on 2020/4/15 0:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends Provider {

    //序列化--方便数据传输
    private static final long SerialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
