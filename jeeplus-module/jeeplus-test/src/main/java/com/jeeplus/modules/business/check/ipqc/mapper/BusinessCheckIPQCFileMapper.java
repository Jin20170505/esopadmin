package com.jeeplus.modules.business.check.ipqc.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.business.check.ipqc.entity.BusinessCheckIPQCFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BusinessCheckIPQCFileMapper extends BaseMapper<BusinessCheckIPQCFile> {
}
