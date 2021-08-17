package com.kas.fullstack.comment.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kas.fullstack.comment.dao.CommentDao;
import com.kas.fullstack.comment.dto.CommentDto;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	@Transactional() //AOP
	public List<CommentDto> commentWrite(CommentDto comment) {
		commentDao.commentWrite(comment);
		HashMap<String,Integer> hm = new HashMap< >();
		hm.put("articleNum",comment.getArticleNum());
		hm.put("firstNum", 1);
		hm.put("commentRow",10);
		return commentDao.commentRead(hm);
	}
	
//	@Override
//	public List<CommentDto> commentWrite(int articleNum, int commentRow) {
//		HashMap<String,Integer> hm = new HashMap< >();
//		hm.put("articleNum",articleNum);
//		hm.put("firstNum", 1);
//		hm.put("commentRow",10);
//		return commentDao.commentRead(hm);
//	}

	@Override
	public List<CommentDto> commentRead(CommentDto comment, int firstNum, int commentRow) {
		HashMap<String,Integer> hm = new HashMap< >();
		hm.put("articleNum",comment.getArticleNum());
		hm.put("firstNum", firstNum);
		hm.put("commentRow",commentRow);
		return commentDao.commentRead(hm);
	}

}
