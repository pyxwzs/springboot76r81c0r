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

import com.entity.ExampaperEntity;
import com.entity.view.ExampaperView;

import com.service.ExampaperService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.ExamquestionService;
import com.service.ExamrecordService;
import com.entity.ExamquestionEntity;
import com.entity.ExamrecordEntity;

/**
 * 在线测试
 * 后端接口
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
@RestController
@RequestMapping("/exampaper")
public class ExampaperController {
    @Autowired
    private ExampaperService exampaperService;




    @Autowired
    private ExamquestionService examquestionService;
    @Autowired
    private ExamrecordService examrecordService;






    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExampaperEntity exampaper,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ExampaperEntity> ew = new EntityWrapper<ExampaperEntity>();


        //查询结果
		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ExampaperEntity exampaper,
                @RequestParam(required = false) Double timestart,
                @RequestParam(required = false) Double timeend,
                @RequestParam(required = false) Double examnumstart,
                @RequestParam(required = false) Double examnumend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ExampaperEntity> ew = new EntityWrapper<ExampaperEntity>();
        if(timestart!=null) ew.ge("time", timestart);
        if(timeend!=null) ew.le("time", timeend);
        if(examnumstart!=null) ew.ge("examnum", examnumstart);
        if(examnumend!=null) ew.le("examnum", examnumend);

        //查询结果
		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExampaperEntity exampaper){
       	EntityWrapper<ExampaperEntity> ew = new EntityWrapper<ExampaperEntity>();
      	ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper"));
        return R.ok().put("data", exampaperService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExampaperEntity exampaper){
        EntityWrapper< ExampaperEntity> ew = new EntityWrapper< ExampaperEntity>();
 		ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper"));
		ExampaperView exampaperView =  exampaperService.selectView(ew);
		return R.ok("查询在线测试成功").put("data", exampaperView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExampaperEntity exampaper = exampaperService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(exampaper,deSens);
        return R.ok().put("data", exampaper);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExampaperEntity exampaper = exampaperService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(exampaper,deSens);
        return R.ok().put("data", exampaper);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增在线测试")
    public R save(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        exampaperService.insert(exampaper);
        return R.ok().put("data",exampaper.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增在线测试")
    @RequestMapping("/add")
    public R add(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        exampaperService.insert(exampaper);
        return R.ok().put("data",exampaper.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改在线测试")
    public R update(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        //全部更新
        exampaperService.updateById(exampaper);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除在线测试")
    public R delete(@RequestBody Long[] ids){
        exampaperService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
