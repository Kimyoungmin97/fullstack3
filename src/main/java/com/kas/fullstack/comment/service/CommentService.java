package com.kas.fullstack.comment.service;

import java.util.List;

import com.kas.fullstack.comment.dto.CommentDto;

public interface CommentService {
	public List<CommentDto> commentWrite(CommentDto comment);
//	public List<CommentDto> commentWrite(int articleNum, int commentRow);
	public List<CommentDto> commentRead(CommentDto comment,int firstNum,int commentRow);
}
