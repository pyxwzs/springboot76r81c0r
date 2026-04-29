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


import com.dao.YinhuanshangbaoDao;
import com.entity.YinhuanshangbaoEntity;
import com.service.YinhuanshangbaoService;
import com.entity.vo.YinhuanshangbaoVO;
import com.entity.view.YinhuanshangbaoView;

@Service("yinhuanshangbaoService")
public class YinhuanshangbaoServiceImpl extends ServiceImpl<YinhuanshangbaoDao, YinhuanshangbaoEntity> implements YinhuanshangbaoService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<YinhuanshangbaoEntity> page = this.selectPage(
                new Query<YinhuanshangbaoEntity>(params).getPage(),
                new EntityWrapper<YinhuanshangbaoEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<YinhuanshangbaoEntity> wrapper) {
		  Page<YinhuanshangbaoView> page =new Query<YinhuanshangbaoView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<YinhuanshangbaoVO> selectListVO(Wrapper<YinhuanshangbaoEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public YinhuanshangbaoVO selectVO(Wrapper<YinhuanshangbaoEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<YinhuanshangbaoView> selectListView(Wrapper<YinhuanshangbaoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public YinhuanshangbaoView selectView(Wrapper<YinhuanshangbaoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
