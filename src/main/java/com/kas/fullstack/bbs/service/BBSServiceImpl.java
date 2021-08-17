package com.kas.fullstack.bbs.service;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kas.fullstack.bbs.common.FileSaveHelper;
import com.kas.fullstack.bbs.dao.BBSDao;
import com.kas.fullstack.bbs.dto.BBSDto;
import com.kas.fullstack.bbs.dto.FileDto;

@Service
public class BBSServiceImpl implements BBSService {

	@Autowired
	private BBSDao bbsDao;

	@Autowired
	private FileSaveHelper fileSaveHelper;
	
	@Resource(name="saveDir")//@Resource�� �̸����� ã�Ƽ� DI����
	private String saveDir;
	
	@Override
	@Transactional
	public void write(BBSDto article,List<MultipartFile> fileUpload) {
//		if(fileUpload.size()!=0) { ������ ����
//		if(!fileUpload.isEmpty()) {������ ���� ����
//			System.out.println("���Ͼ��ε尡 �ֽ��ϴ�.");
//		}
//		List<MultipartFile>�� CommonsMultipartFile�� ������
//		�׷��� isEmpty()(List�� �ִ� isEmpty()�޼ҵ���)�� ����� List���� ����ִ� ���� �ƴ�
//		MultipartFile�� �ִ� isEmpty()�� ����ؾ��� 
		bbsDao.write(article);
		if(!fileUpload.get(0).isEmpty()) {
			for(MultipartFile mf:fileUpload) {
				String savedFileName = fileSaveHelper.save(mf);
				FileDto fileDto = new FileDto();
				fileDto.setOriginalFileName(mf.getOriginalFilename());
				fileDto.setSavedFileName(savedFileName);
				fileDto.setArticleNum(article.getArticleNum());
				bbsDao.insertFile(fileDto);
			}
		}
	}

	@Override
	public List<BBSDto> list() {

		return bbsDao.list();
	}

	@Override
	public BBSDto content(int articleNum) {

		return bbsDao.content(articleNum);
	}

	@Override
	public String login(String id, String pass, HttpSession session) {
		String dbPass = bbsDao.login(id);
		if(dbPass == null) {
			System.out.println("ȸ���� �ƴ�");
			return null;
		}else if(dbPass.equals(pass)) {
			session.setAttribute("id",id);
			return "redirect:/list.bbs";
		}else {
			return "login";
		}
	}

	@Override
	public void delete(int articleNum) {
		bbsDao.delete(articleNum);
	}

	@Override
	public BBSDto getUpdateArticle(String articleNum) {
		return bbsDao.getUpdateArticle(articleNum);
	}

	@Override
	public void updateArticle(BBSDto article) {
		bbsDao.updateArticle(article);		
	}

	@Override
	public List<FileDto> getFiles(int articleNum) {
		return bbsDao.getFiles(articleNum);
	}

	@Override
	public FileSystemResource download(String savedFileName, HttpServletResponse response) {
		
		response.setContentType("application/download");
		
		String originalFileName = savedFileName.substring(savedFileName.indexOf("_")+1);
		
		try {
			originalFileName = URLEncoder.encode(originalFileName,"utf-8").replace("+","%20")
					.replace("%28","(").replace("%29",")");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setHeader("Content-Disposition", "attachment;"+" filename=\""+originalFileName+"\";");
		FileSystemResource fsr = new FileSystemResource(saveDir+savedFileName);
		
		return fsr;
	}

	@Override
	public int commentCount(int articleNum) {
		return bbsDao.commentCount(articleNum);
	}
	
	
}













