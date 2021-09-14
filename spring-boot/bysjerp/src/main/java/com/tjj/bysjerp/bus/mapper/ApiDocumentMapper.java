package com.tjj.bysjerp.bus.mapper;

import com.tjj.bysjerp.bus.domain.ApiDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiDocumentMapper {

    public int add(ApiDocument ApiDocument);
    public int update(ApiDocument ApiDocument);
    public int delete(@Param("id") int id);

    public ApiDocument load(@Param("id") int id);
    public List<ApiDocument> loadAll(@Param("projectId") int projectId,
                                     @Param("groupId") int groupId);

    public List<ApiDocument> loadByGroupId(@Param("groupId") int groupId);

    List<ApiDocument> findByResponseDataTypeId(@Param("responseDatatypeId") int responseDatatypeId);
}
