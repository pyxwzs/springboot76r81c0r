package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 测试统计
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
@TableName("examstatistics")
public class ExamstatisticsEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamstatisticsEntity() {
		
	}
	
	public ExamstatisticsEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
					
	private String username;
	
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
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
