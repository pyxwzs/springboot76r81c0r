package com.entity.vo;

import com.entity.ExampaperEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 在线测试
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
public class ExampaperVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 考试时长(分钟)
	 */
	
	private Integer time;
		
	/**
	 * 试卷状态
	 */
	
	private String status;
		
	/**
	 * 限考次数
	 */
	
	private Integer examnum;
				
	
	/**
	 * 设置：考试时长(分钟)
	 */
	 
	public void setTime(Integer time) {
		this.time = time;
	}
	
	/**
	 * 获取：考试时长(分钟)
	 */
	public Integer getTime() {
		return time;
	}
				
	
	/**
	 * 设置：试卷状态
	 */
	 
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 获取：试卷状态
	 */
	public String getStatus() {
		return status;
	}
				
	
	/**
	 * 设置：限考次数
	 */
	 
	public void setExamnum(Integer examnum) {
		this.examnum = examnum;
	}
	
	/**
	 * 获取：限考次数
	 */
	public Integer getExamnum() {
		return examnum;
	}
			
}
