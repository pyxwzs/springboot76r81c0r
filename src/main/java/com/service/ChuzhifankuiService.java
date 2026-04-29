package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChuzhifankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ChuzhifankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ChuzhifankuiView;


/**
 * 处置反馈
 *
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
public interface ChuzhifankuiService extends IService<ChuzhifankuiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ChuzhifankuiVO> selectListVO(Wrapper<ChuzhifankuiEntity> wrapper);
   	
   	ChuzhifankuiVO selectVO(@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);
   	
   	List<ChuzhifankuiView> selectListView(Wrapper<ChuzhifankuiEntity> wrapper);
   	
   	ChuzhifankuiView selectView(@Param("ew") Wrapper<ChuzhifankuiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ChuzhifankuiEntity> wrapper);

   	

}

