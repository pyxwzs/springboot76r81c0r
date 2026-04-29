package com.dao;

import com.entity.ExamstatisticsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ExamstatisticsVO;
import com.entity.view.ExamstatisticsView;


/**
 * 测试统计
 * 
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
public interface ExamstatisticsDao extends BaseMapper<ExamstatisticsEntity> {
	
	List<ExamstatisticsVO> selectListVO(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);
	
	ExamstatisticsVO selectVO(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);
	
	List<ExamstatisticsView> selectListView(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);

	List<ExamstatisticsView> selectListView(Pagination page,@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);

	
	ExamstatisticsView selectView(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);
	

    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);

	/** 按试卷 ID 分组平均分（避免仅按试卷名称分组时同名合并成一条） */
	List<Map<String, Object>> selectPaperAvgGroupByPaperid(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);

}
