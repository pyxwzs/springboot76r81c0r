package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.ExamstatisticsDao;
import com.entity.ExamstatisticsEntity;
import com.service.ExamstatisticsService;
import com.entity.vo.ExamstatisticsVO;
import com.entity.view.ExamstatisticsView;

@Service("examstatisticsService")
public class ExamstatisticsServiceImpl extends ServiceImpl<ExamstatisticsDao, ExamstatisticsEntity> implements ExamstatisticsService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamstatisticsEntity> page = this.selectPage(
                new Query<ExamstatisticsEntity>(params).getPage(),
                new EntityWrapper<ExamstatisticsEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamstatisticsEntity> wrapper) {
		  Page<ExamstatisticsView> page =new Query<ExamstatisticsView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ExamstatisticsVO> selectListVO(Wrapper<ExamstatisticsEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamstatisticsVO selectVO(Wrapper<ExamstatisticsEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamstatisticsView> selectListView(Wrapper<ExamstatisticsEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamstatisticsView selectView(Wrapper<ExamstatisticsEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, Wrapper<ExamstatisticsEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, Wrapper<ExamstatisticsEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, Wrapper<ExamstatisticsEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }

	@Override
	public List<Map<String, Object>> selectPaperAvgGroupByPaperid(Wrapper<ExamstatisticsEntity> wrapper) {
		return baseMapper.selectPaperAvgGroupByPaperid(wrapper);
	}




}
