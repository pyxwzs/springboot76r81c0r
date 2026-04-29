package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.YinhuanshangbaoEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.YinhuanshangbaoVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.YinhuanshangbaoView;


/**
 * 隐患上报
 *
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
public interface YinhuanshangbaoService extends IService<YinhuanshangbaoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<YinhuanshangbaoVO> selectListVO(Wrapper<YinhuanshangbaoEntity> wrapper);
   	
   	YinhuanshangbaoVO selectVO(@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);
   	
   	List<YinhuanshangbaoView> selectListView(Wrapper<YinhuanshangbaoEntity> wrapper);
   	
   	YinhuanshangbaoView selectView(@Param("ew") Wrapper<YinhuanshangbaoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<YinhuanshangbaoEntity> wrapper);

   	

}

