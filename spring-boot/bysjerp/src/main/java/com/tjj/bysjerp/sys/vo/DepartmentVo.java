package com.tjj.bysjerp.sys.vo;

import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.domain.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Owen on 2020/4/7 16:13
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentVo extends Department {

    //序列化
    private static final long SerialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;


}
