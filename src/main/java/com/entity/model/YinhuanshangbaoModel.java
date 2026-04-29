package com.entity.model;

import com.entity.YinhuanshangbaoEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 隐患上报
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2026-02-04 23:31:22
 */
public class YinhuanshangbaoModel  implements Serializable {
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
	 * 上报日期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date shangbaoriqi;
		
	/**
	 * 隐患状态
	 */
	
	private String yinhuanzhuangtai;
		
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
	 * 设置：上报日期
	 */
	 
	public void setShangbaoriqi(Date shangbaoriqi) {
		this.shangbaoriqi = shangbaoriqi;
	}
	
	/**
	 * 获取：上报日期
	 */
	public Date getShangbaoriqi() {
		return shangbaoriqi;
	}
				
	
	/**
	 * 设置：隐患状态
	 */
	 
	public void setYinhuanzhuangtai(String yinhuanzhuangtai) {
		this.yinhuanzhuangtai = yinhuanzhuangtai;
	}
	
	/**
	 * 获取：隐患状态
	 */
	public String getYinhuanzhuangtai() {
		return yinhuanzhuangtai;
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
			
}
