package com.entity.vo;

import com.entity.ExamstatisticsEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 测试统计
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
public class ExamstatisticsVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 试卷id
	 */
	
	private Long paperid;
		
	/**
	 * 试卷名称
	 */
	
	private String papername;
		
	/**
	 * 考试编号
	 */
	
	private String examno;
		
	/**
	 * 总分
	 */
	
	private Double totalscore;
		
	/**
	 * 用户id
	 */
	
	private Long userid;
				
	
	/**
	 * 设置：试卷id
	 */
	 
	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
	
	/**
	 * 获取：试卷id
	 */
	public Long getPaperid() {
		return paperid;
	}
				
	
	/**
	 * 设置：试卷名称
	 */
	 
	public void setPapername(String papername) {
		this.papername = papername;
	}
	
	/**
	 * 获取：试卷名称
	 */
	public String getPapername() {
		return papername;
	}
				
	
	/**
	 * 设置：考试编号
	 */
	 
	public void setExamno(String examno) {
		this.examno = examno;
	}
	
	/**
	 * 获取：考试编号
	 */
	public String getExamno() {
		return examno;
	}
				
	
	/**
	 * 设置：总分
	 */
	 
	public void setTotalscore(Double totalscore) {
		this.totalscore = totalscore;
	}
	
	/**
	 * 获取：总分
	 */
	public Double getTotalscore() {
		return totalscore;
	}
				
	
	/**
	 * 设置：用户id
	 */
	 
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	/**
	 * 获取：用户id
	 */
	public Long getUserid() {
		return userid;
	}
			
}
