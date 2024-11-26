package com.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.domain.Judge;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Himit_ZH
 * @since 2020-10-23
 */
@Mapper
@Repository
public interface JudgeMapper extends BaseMapper<Judge> {

}
