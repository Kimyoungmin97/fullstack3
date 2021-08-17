package com.kas.fullstack.comment.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {
	private int commentNum;
	private String id;
	private String commentContent;
	private Timestamp commentDate;
	private int articleNum;

}
