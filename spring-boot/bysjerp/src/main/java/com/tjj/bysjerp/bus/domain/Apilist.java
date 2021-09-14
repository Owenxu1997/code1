package com.tjj.bysjerp.bus.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Owen Xu
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_apilist")
public class Apilist implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "apiId", type = IdType.AUTO)
    private Integer apiId;

    @TableField("apiName")
    private String apiName;

    @TableField("apiType")
    private String apiType;

    private String url;

    private Integer tested;

    private String description;

    @TableField("param1Key")
    private String param1Key;

    @TableField("param2Key")
    private String param2Key;

    @TableField("param1Value")
    private String param1Value;

    @TableField("param2Value")
    private String param2Value;

    private Integer reviewed;

    private Integer uid;


}
