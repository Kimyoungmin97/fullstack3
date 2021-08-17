package com.kas.fullstack.bbs.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import com.kas.fullstack.bbs.dto.BBSDto;
import com.kas.fullstack.bbs.dto.FileDto;

public interface BBSService {
	public void write(BBSDto article, List<MultipartFile> fileUpload);
	public List<BBSDto> list();
	public BBSDto content(int articleNum);
	public String login(String id, String pass, HttpSession session);
	public void delete(int articleNum);
	public BBSDto getUpdateArticle(String articleNum);
	public void updateArticle(BBSDto article);
	public List<FileDto> getFiles(int articleNum);
	public FileSystemResource download(String savedFileName, HttpServletResponse res);
	public int commentCount(int articleNum);
}
