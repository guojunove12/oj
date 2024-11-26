package com.oj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.domain.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: guojun
 * 2024-11-22 14:31
 */

@Mapper
@Repository
public interface ProblemMapper extends BaseMapper<Problem> {
}
