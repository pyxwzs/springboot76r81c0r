package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import com.utils.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.YinhuanshangbaoEntity;
import com.entity.view.YinhuanshangbaoView;

import com.service.YinhuanshangbaoService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 隐患上报
 * 后端接口
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
@RestController
@RequestMapping("/yinhuanshangbao")
public class YinhuanshangbaoController {
    @Autowired
    private YinhuanshangbaoService yinhuanshangbaoService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YinhuanshangbaoEntity yinhuanshangbao,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			yinhuanshangbao.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<YinhuanshangbaoEntity> ew = new EntityWrapper<YinhuanshangbaoEntity>();


        //查询结果
		PageUtils page = yinhuanshangbaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yinhuanshangbao), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,YinhuanshangbaoEntity yinhuanshangbao,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangbaoriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date shangbaoriqiend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YinhuanshangbaoEntity> ew = new EntityWrapper<YinhuanshangbaoEntity>();
        if(shangbaoriqistart!=null) ew.ge("shangbaoriqi", shangbaoriqistart);
        if(shangbaoriqiend!=null) ew.le("shangbaoriqi", shangbaoriqiend);

        //查询结果
		PageUtils page = yinhuanshangbaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yinhuanshangbao), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YinhuanshangbaoEntity yinhuanshangbao){
       	EntityWrapper<YinhuanshangbaoEntity> ew = new EntityWrapper<YinhuanshangbaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yinhuanshangbao, "yinhuanshangbao"));
        return R.ok().put("data", yinhuanshangbaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YinhuanshangbaoEntity yinhuanshangbao){
        EntityWrapper< YinhuanshangbaoEntity> ew = new EntityWrapper< YinhuanshangbaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yinhuanshangbao, "yinhuanshangbao"));
		YinhuanshangbaoView yinhuanshangbaoView =  yinhuanshangbaoService.selectView(ew);
		return R.ok("查询隐患上报成功").put("data", yinhuanshangbaoView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YinhuanshangbaoEntity yinhuanshangbao = yinhuanshangbaoService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yinhuanshangbao,deSens);
        return R.ok().put("data", yinhuanshangbao);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YinhuanshangbaoEntity yinhuanshangbao = yinhuanshangbaoService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yinhuanshangbao,deSens);
        return R.ok().put("data", yinhuanshangbao);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增隐患上报")
    public R save(@RequestBody YinhuanshangbaoEntity yinhuanshangbao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yinhuanshangbao);
        yinhuanshangbaoService.insert(yinhuanshangbao);
        return R.ok().put("data",yinhuanshangbao.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增隐患上报")
    @RequestMapping("/add")
    public R add(@RequestBody YinhuanshangbaoEntity yinhuanshangbao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yinhuanshangbao);
        yinhuanshangbaoService.insert(yinhuanshangbao);
        return R.ok().put("data",yinhuanshangbao.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改隐患上报")
    public R update(@RequestBody YinhuanshangbaoEntity yinhuanshangbao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yinhuanshangbao);
        //全部更新
        yinhuanshangbaoService.updateById(yinhuanshangbao);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除隐患上报")
    public R delete(@RequestBody Long[] ids){
        yinhuanshangbaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
