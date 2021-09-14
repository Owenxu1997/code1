package com.tjj.bysjerp.bus.vo;

import com.tjj.bysjerp.bus.domain.Inport;
import com.tjj.bysjerp.bus.domain.Outport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Owen on 2020/4/7 16:13
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class OutportVo extends Outport {

    //序列化
    private static final long SerialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
