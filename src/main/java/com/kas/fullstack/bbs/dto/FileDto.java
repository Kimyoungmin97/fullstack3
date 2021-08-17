package com.kas.fullstack.bbs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDto {
	private int fileNum;
	private String originalFileName;
	private String savedFileName;
	private int articleNum;
}
