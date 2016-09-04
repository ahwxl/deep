package com.bplow.deep.bpm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bplow.deep.bpm.domain.AutoAppInfo;

public interface AutoAppInfoMapper {
	
    int countByExample(AutoAppInfo example);

    int insert(AutoAppInfo record);
    
    int delete(String record);

    List<AutoAppInfo> queryForPage(AutoAppInfo example);

    int updateByExampleSelective(@Param("record") AutoAppInfo record, @Param("example") AutoAppInfo example);
}