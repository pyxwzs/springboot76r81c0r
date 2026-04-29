package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExamstatisticsEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ExamstatisticsVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ExamstatisticsView;


/**
 * 测试统计
 *
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
public interface ExamstatisticsService extends IService<ExamstatisticsEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamstatisticsVO> selectListVO(Wrapper<ExamstatisticsEntity> wrapper);
   	
   	ExamstatisticsVO selectVO(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);
   	
   	List<ExamstatisticsView> selectListView(Wrapper<ExamstatisticsEntity> wrapper);
   	
   	ExamstatisticsView selectView(@Param("ew") Wrapper<ExamstatisticsEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ExamstatisticsEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<ExamstatisticsEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<ExamstatisticsEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<ExamstatisticsEntity> wrapper);

	List<Map<String, Object>> selectPaperAvgGroupByPaperid(Wrapper<ExamstatisticsEntity> wrapper);

}

