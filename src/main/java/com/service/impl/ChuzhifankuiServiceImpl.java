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


import com.dao.ChuzhifankuiDao;
import com.entity.ChuzhifankuiEntity;
import com.service.ChuzhifankuiService;
import com.entity.vo.ChuzhifankuiVO;
import com.entity.view.ChuzhifankuiView;

@Service("chuzhifankuiService")
public class ChuzhifankuiServiceImpl extends ServiceImpl<ChuzhifankuiDao, ChuzhifankuiEntity> implements ChuzhifankuiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ChuzhifankuiEntity> page = this.selectPage(
                new Query<ChuzhifankuiEntity>(params).getPage(),
                new EntityWrapper<ChuzhifankuiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ChuzhifankuiEntity> wrapper) {
		  Page<ChuzhifankuiView> page =new Query<ChuzhifankuiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ChuzhifankuiVO> selectListVO(Wrapper<ChuzhifankuiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ChuzhifankuiVO selectVO(Wrapper<ChuzhifankuiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ChuzhifankuiView> selectListView(Wrapper<ChuzhifankuiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ChuzhifankuiView selectView(Wrapper<ChuzhifankuiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
