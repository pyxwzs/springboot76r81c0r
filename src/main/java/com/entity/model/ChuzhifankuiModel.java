package com.entity.model;

import com.entity.ChuzhifankuiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 处置反馈
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
public class ChuzhifankuiModel  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 隐患名称
	 */
	
	private String yinhuanmingcheng;
		
	/**
	 * 隐患类型
	 */
	
	private String yinhuanleixing;
		
	/**
	 * 隐患位置
	 */
	
	private String yinhuanweizhi;
		
	/**
	 * 隐患等级
	 */
	
	private String yinhuandengji;
		
	/**
	 * 隐患内容
	 */
	
	private String yinhuanneirong;
		
	/**
	 * 账号
	 */
	
	private String zhanghao;
		
	/**
	 * 姓名
	 */
	
	private String xingming;
		
	/**
	 * 手机号码
	 */
	
	private String shoujihaoma;
		
	/**
	 * 处理结果
	 */
	
	private String chulijieguo;
		
	/**
	 * 处理时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date chulishijian;
				
	
	/**
	 * 设置：隐患名称
	 */
	 
	public void setYinhuanmingcheng(String yinhuanmingcheng) {
		this.yinhuanmingcheng = yinhuanmingcheng;
	}
	
	/**
	 * 获取：隐患名称
	 */
	public String getYinhuanmingcheng() {
		return yinhuanmingcheng;
	}
				
	
	/**
	 * 设置：隐患类型
	 */
	 
	public void setYinhuanleixing(String yinhuanleixing) {
		this.yinhuanleixing = yinhuanleixing;
	}
	
	/**
	 * 获取：隐患类型
	 */
	public String getYinhuanleixing() {
		return yinhuanleixing;
	}
				
	
	/**
	 * 设置：隐患位置
	 */
	 
	public void setYinhuanweizhi(String yinhuanweizhi) {
		this.yinhuanweizhi = yinhuanweizhi;
	}
	
	/**
	 * 获取：隐患位置
	 */
	public String getYinhuanweizhi() {
		return yinhuanweizhi;
	}
				
	
	/**
	 * 设置：隐患等级
	 */
	 
	public void setYinhuandengji(String yinhuandengji) {
		this.yinhuandengji = yinhuandengji;
	}
	
	/**
	 * 获取：隐患等级
	 */
	public String getYinhuandengji() {
		return yinhuandengji;
	}
				
	
	/**
	 * 设置：隐患内容
	 */
	 
	public void setYinhuanneirong(String yinhuanneirong) {
		this.yinhuanneirong = yinhuanneirong;
	}
	
	/**
	 * 获取：隐患内容
	 */
	public String getYinhuanneirong() {
		return yinhuanneirong;
	}
				
	
	/**
	 * 设置：账号
	 */
	 
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	
	/**
	 * 获取：账号
	 */
	public String getZhanghao() {
		return zhanghao;
	}
				
	
	/**
	 * 设置：姓名
	 */
	 
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	
	/**
	 * 获取：姓名
	 */
	public String getXingming() {
		return xingming;
	}
				
	
	/**
	 * 设置：手机号码
	 */
	 
	public void setShoujihaoma(String shoujihaoma) {
		this.shoujihaoma = shoujihaoma;
	}
	
	/**
	 * 获取：手机号码
	 */
	public String getShoujihaoma() {
		return shoujihaoma;
	}
				
	
	/**
	 * 设置：处理结果
	 */
	 
	public void setChulijieguo(String chulijieguo) {
		this.chulijieguo = chulijieguo;
	}
	
	/**
	 * 获取：处理结果
	 */
	public String getChulijieguo() {
		return chulijieguo;
	}
				
	
	/**
	 * 设置：处理时间
	 */
	 
	public void setChulishijian(Date chulishijian) {
		this.chulishijian = chulishijian;
	}
	
	/**
	 * 获取：处理时间
	 */
	public Date getChulishijian() {
		return chulishijian;
	}
			
}
