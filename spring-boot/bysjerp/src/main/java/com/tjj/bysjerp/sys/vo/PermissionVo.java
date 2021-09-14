package com.tjj.bysjerp.sys.vo;

import com.tjj.bysjerp.sys.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Owen on 2020/4/7 16:13
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    //序列化
    private static final long SerialVersionUID = 1L;

    //加入分页传递条件
    private Integer page = 1;
    private Integer limit = 10;
}
