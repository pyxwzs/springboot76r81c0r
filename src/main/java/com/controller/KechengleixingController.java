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

import com.entity.KechengleixingEntity;
import com.entity.view.KechengleixingView;

import com.service.KechengleixingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 课程类型
 * 后端接口
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
@RestController
@RequestMapping("/kechengleixing")
public class KechengleixingController {
    @Autowired
    private KechengleixingService kechengleixingService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,KechengleixingEntity kechengleixing,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<KechengleixingEntity> ew = new EntityWrapper<KechengleixingEntity>();


        //查询结果
		PageUtils page = kechengleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengleixing), params), params));
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
    public R list(@RequestParam Map<String, Object> params,KechengleixingEntity kechengleixing,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<KechengleixingEntity> ew = new EntityWrapper<KechengleixingEntity>();

        //查询结果
		PageUtils page = kechengleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengleixing), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( KechengleixingEntity kechengleixing){
       	EntityWrapper<KechengleixingEntity> ew = new EntityWrapper<KechengleixingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( kechengleixing, "kechengleixing"));
        return R.ok().put("data", kechengleixingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(KechengleixingEntity kechengleixing){
        EntityWrapper< KechengleixingEntity> ew = new EntityWrapper< KechengleixingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( kechengleixing, "kechengleixing"));
		KechengleixingView kechengleixingView =  kechengleixingService.selectView(ew);
		return R.ok("查询课程类型成功").put("data", kechengleixingView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        KechengleixingEntity kechengleixing = kechengleixingService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(kechengleixing,deSens);
        return R.ok().put("data", kechengleixing);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        KechengleixingEntity kechengleixing = kechengleixingService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(kechengleixing,deSens);
        return R.ok().put("data", kechengleixing);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增课程类型")
    public R save(@RequestBody KechengleixingEntity kechengleixing, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(kechengleixingService.selectCount(new EntityWrapper<KechengleixingEntity>().eq("kechengleixing", kechengleixing.getKechengleixing()))>0) {
            return R.error("课程类型已存在");
        }
        //ValidatorUtils.validateEntity(kechengleixing);
        kechengleixingService.insert(kechengleixing);
        return R.ok().put("data",kechengleixing.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增课程类型")
    @RequestMapping("/add")
    public R add(@RequestBody KechengleixingEntity kechengleixing, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(kechengleixingService.selectCount(new EntityWrapper<KechengleixingEntity>().eq("kechengleixing", kechengleixing.getKechengleixing()))>0) {
            return R.error("课程类型已存在");
        }
        //ValidatorUtils.validateEntity(kechengleixing);
        kechengleixingService.insert(kechengleixing);
        return R.ok().put("data",kechengleixing.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改课程类型")
    public R update(@RequestBody KechengleixingEntity kechengleixing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(kechengleixing);
        //验证字段唯一性，否则返回错误信息
        if(kechengleixingService.selectCount(new EntityWrapper<KechengleixingEntity>().ne("id", kechengleixing.getId()).eq("kechengleixing", kechengleixing.getKechengleixing()))>0) {
            return R.error("课程类型已存在");
        }
        //全部更新
        kechengleixingService.updateById(kechengleixing);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除课程类型")
    public R delete(@RequestBody Long[] ids){
        kechengleixingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
