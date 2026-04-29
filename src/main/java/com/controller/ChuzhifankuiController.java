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

import com.entity.ChuzhifankuiEntity;
import com.entity.view.ChuzhifankuiView;

import com.service.ChuzhifankuiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 处置反馈
 * 后端接口
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
@RestController
@RequestMapping("/chuzhifankui")
public class ChuzhifankuiController {
    @Autowired
    private ChuzhifankuiService chuzhifankuiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChuzhifankuiEntity chuzhifankui,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			chuzhifankui.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<ChuzhifankuiEntity> ew = new EntityWrapper<ChuzhifankuiEntity>();


        //查询结果
		PageUtils page = chuzhifankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chuzhifankui), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ChuzhifankuiEntity chuzhifankui,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date chulishijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date chulishijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChuzhifankuiEntity> ew = new EntityWrapper<ChuzhifankuiEntity>();
        if(chulishijianstart!=null) ew.ge("chulishijian", chulishijianstart);
        if(chulishijianend!=null) ew.le("chulishijian", chulishijianend);

        //查询结果
		PageUtils page = chuzhifankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chuzhifankui), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChuzhifankuiEntity chuzhifankui){
       	EntityWrapper<ChuzhifankuiEntity> ew = new EntityWrapper<ChuzhifankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chuzhifankui, "chuzhifankui"));
        return R.ok().put("data", chuzhifankuiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChuzhifankuiEntity chuzhifankui){
        EntityWrapper< ChuzhifankuiEntity> ew = new EntityWrapper< ChuzhifankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chuzhifankui, "chuzhifankui"));
		ChuzhifankuiView chuzhifankuiView =  chuzhifankuiService.selectView(ew);
		return R.ok("查询处置反馈成功").put("data", chuzhifankuiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChuzhifankuiEntity chuzhifankui = chuzhifankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chuzhifankui,deSens);
        return R.ok().put("data", chuzhifankui);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChuzhifankuiEntity chuzhifankui = chuzhifankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chuzhifankui,deSens);
        return R.ok().put("data", chuzhifankui);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增处置反馈")
    public R save(@RequestBody ChuzhifankuiEntity chuzhifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chuzhifankui);
        chuzhifankuiService.insert(chuzhifankui);
        return R.ok().put("data",chuzhifankui.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增处置反馈")
    @RequestMapping("/add")
    public R add(@RequestBody ChuzhifankuiEntity chuzhifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chuzhifankui);
        chuzhifankuiService.insert(chuzhifankui);
        return R.ok().put("data",chuzhifankui.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改处置反馈")
    public R update(@RequestBody ChuzhifankuiEntity chuzhifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chuzhifankui);
        //全部更新
        chuzhifankuiService.updateById(chuzhifankui);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除处置反馈")
    public R delete(@RequestBody Long[] ids){
        chuzhifankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
