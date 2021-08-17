package com.kas.fullstack.bbs.controler;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kas.fullstack.bbs.dto.BBSDto;
import com.kas.fullstack.bbs.service.BBSService;

@Controller
public class BBSController {
	
	@Autowired
	private BBSService bbsService;
	
//	@RequestMappint("/list.bbs") get와 post둘다 해당
//	@RequestMapping(value="/list.bbs", method = RequestMethod.GET)
//	@PostMapping("list.bbs")
	@GetMapping("/list.bbs")//void면 매핑으로 받는 list가 뷰이름이 된다.
	public void list(Model model) {
		model.addAttribute("articleList", bbsService.list());
	}
	
	@GetMapping("/write.bbs")
	public String writeForm(HttpSession session) {
		return "write";
	}
	
//	1개의 파일은 1개의 MultiPartFile과 매칭된다
	@PostMapping("/write.bbs")
//	public String write(@RequestParam String title, @RequestParam String content) {
	public String write(BBSDto article, @RequestPart("fileUpload") List<MultipartFile> fileUpload) {
		bbsService.write(article,fileUpload);
		return "redirect:/list.bbs";
	}
	
	@GetMapping("/content.bbs")
	public String content(Model model,@RequestParam int articleNum) {
		model.addAttribute("article",bbsService.content(articleNum));
		model.addAttribute("fileList",bbsService.getFiles(articleNum));
		model.addAttribute("commentCount",bbsService.commentCount(articleNum));
		return "content";
	}
	
	@GetMapping("/login.bbs")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login.bbs")
	public String login(@RequestParam String id,@RequestParam String pass,
			HttpSession session) {
		return bbsService.login(id,pass,session);
	}
	
	@PostMapping("/logout.bbs")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/list.bbs";
	}
	
	@GetMapping("/delete.bbs")
	public String delete(@RequestParam int articleNum) {
		bbsService.delete(articleNum);
		return "redirect:/list.bbs";
	}
	
	@GetMapping("/update.bbs")
	public String updateForm(@RequestParam String articleNum,Model model) {
		model.addAttribute("article",bbsService.getUpdateArticle(articleNum));
		return "updateForm";
	}
	
	@PostMapping("update.bbs")
	public String update(BBSDto article) {
		bbsService.updateArticle(article);
		return "redirect:/content.bbs?articleNum="+article.getArticleNum();
	}
	
	@GetMapping("/download.bbs")
	@ResponseBody
	public FileSystemResource download(@RequestParam String savedFileName,HttpServletResponse res) {
		return bbsService.download(savedFileName,res);
		
	}
	
}






