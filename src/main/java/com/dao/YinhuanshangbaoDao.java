package com.dao;

import com.entity.YinhuanshangbaoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.YinhuanshangbaoVO;
import com.entity.view.YinhuanshangbaoView;


/**
 * 隐患上报
 * 
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
public interface YinhuanshangbaoDao extends BaseMapper<YinhuanshangbaoEntity> {
	
	List<YinhuanshangbaoVO> selectListVO(@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);
	
	YinhuanshangbaoVO selectVO(@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);
	
	List<YinhuanshangbaoView> selectListView(@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);

	List<YinhuanshangbaoView> selectListView(Pagination page,@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);

	
	YinhuanshangbaoView selectView(@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);
	

}
