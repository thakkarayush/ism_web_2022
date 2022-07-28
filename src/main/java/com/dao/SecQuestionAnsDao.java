package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.SecQuestionAnsBean;

@Repository
public class SecQuestionAnsDao {
	
	@Autowired
	JdbcTemplate stmt;
	
	public void addQuestion(SecQuestionAnsBean ansBean) {
		stmt.update("insert into secquestionans (question,ans,userid) values (?,?,?)", ansBean.getQuestion(),
				ansBean.getAns(), ansBean.getUserId());
	}
	
	public List<SecQuestionAnsBean> getQuestionByUser(int userId){
		return stmt.query(
				"select * from  users_table inner join secquestionans on users_table.userid = secquestionans.userid where users_table.userid = ?",
				new BeanPropertyRowMapper<SecQuestionAnsBean>(SecQuestionAnsBean.class), new Object[] { userId });
	
	}
}
