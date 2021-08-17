package com.kas.fullstack.bbs.dao;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kas.fullstack.bbs.dto.BBSDto;
import com.kas.fullstack.bbs.dto.FileDto;

import oracle.net.aso.f;

@Repository
public class BBSDaoImpl implements BBSDao {
	
	private final String nameSpace="com.kas.fullstack.bbs.dao.BBSDao"; 
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void write(BBSDto article) {
		sqlSession.insert(nameSpace+".write",article);
	}

	@Override
	public List<BBSDto> list() {
		return sqlSession.selectList(nameSpace+".list");
	}

	@Override
	public BBSDto content(int articleNum) {
		return sqlSession.selectOne(nameSpace+".content", articleNum);
	}

	@Override
	public String login(String id) {
		return sqlSession.selectOne(nameSpace+".login",id);
	}

	@Override
	public void delete(int articleNum) {
		sqlSession.delete(nameSpace+".delete", articleNum);
		
	}

	@Override
	public BBSDto getUpdateArticle(String articleNum) {
		return sqlSession.selectOne(nameSpace+".getUpdateArticle",articleNum);
	}

	@Override
	public void updateArticle(BBSDto article) {
		sqlSession.update(nameSpace+".updateArticle", article);
	}

	@Override
	public void insertFile(FileDto fileDto) {
		sqlSession.insert(nameSpace+".insertFile",fileDto);
	}

	@Override
	public List<FileDto> getFiles(int articleNum) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(nameSpace+".getFiles",articleNum);
	}

	@Override
	public int commentCount(int articleNum) {
		return sqlSession.selectOne(nameSpace+".commentCount", articleNum);
	}
	
	
	
	
	
}
