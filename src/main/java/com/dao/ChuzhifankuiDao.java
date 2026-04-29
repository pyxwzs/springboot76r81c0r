package com.dao;

import com.entity.ChuzhifankuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChuzhifankuiVO;
import com.entity.view.ChuzhifankuiView;


/**
 * 处置反馈
 * 
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
public interface ChuzhifankuiDao extends BaseMapper<ChuzhifankuiEntity> {
	
	List<ChuzhifankuiVO> selectListVO(@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);
	
	ChuzhifankuiVO selectVO(@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);
	
	List<ChuzhifankuiView> selectListView(@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);

	List<ChuzhifankuiView> selectListView(Pagination page,@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);

	
	ChuzhifankuiView selectView(@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);
	

}
