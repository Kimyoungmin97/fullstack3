package com.kas.fullstack.comment.controler;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kas.fullstack.comment.dto.CommentDto;
import com.kas.fullstack.comment.service.CommentService;

@RestController //컨트롤러에 모든 메소드가 json형태로 받으면 붙음
//@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/commentWrite.comment")
//	@ResponseBody
	public List<CommentDto> commentWrite(CommentDto commentDto, HttpSession session) {
		commentDto.setId((String) session.getAttribute("id"));
		return commentService.commentWrite(commentDto);
	}
	
//	public List<CommentDto> commentRead(@RequestParam int articleNum,@RequestParam int commentRow) {
//		return commentService.commentWrite(articleNum,commentRow);
//	}
	
	@PostMapping("/commentRead.comment")
//	@ResponseBody//json형변환
	public List<CommentDto> commentRead(CommentDto commentDto,@RequestParam int commPageNum,@RequestParam int commentRow) {
		int firstNum = (commPageNum*10)-9;
		return commentService.commentRead(commentDto,firstNum,commentRow);
	}
}
