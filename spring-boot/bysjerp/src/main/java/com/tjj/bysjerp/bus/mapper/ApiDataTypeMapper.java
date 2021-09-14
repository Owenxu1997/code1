package com.tjj.bysjerp.bus.mapper;

import com.tjj.bysjerp.bus.domain.ApiDataType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiDataTypeMapper {

    public int add(ApiDataType ApiDataType);

    public int update(ApiDataType ApiDataType);

    public int delete(@Param("id") int id);

    public ApiDataType load(@Param("id") int id);

    public List<ApiDataType> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("bizId") int bizId,
                                      @Param("name") String name);
    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("bizId") int bizId,
                             @Param("name") String name);

    public ApiDataType loadByName(@Param("name") String name);

}
