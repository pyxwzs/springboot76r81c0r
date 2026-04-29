package com.entity.view;

import com.entity.ExamstatisticsEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 测试统计
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-02-04 23:31:23
 */
@TableName("examstatistics")
public class ExamstatisticsView  extends ExamstatisticsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ExamstatisticsView(){
	}
 
 	public ExamstatisticsView(ExamstatisticsEntity examstatisticsEntity){
 	try {
			BeanUtils.copyProperties(this, examstatisticsEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
