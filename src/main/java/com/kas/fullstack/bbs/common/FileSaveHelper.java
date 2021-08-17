package com.kas.fullstack.bbs.common;
import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component //@Named 스프링이 인식하게 하는거
public class FileSaveHelper {
	
	@Resource(name="saveDir")//@Resource는 이름으로 찾아서 DI해줌
	private String saveDir;
	
	public String save(MultipartFile uploadFile) {
		String savedFileName = UUID.randomUUID().toString()+"_"+uploadFile.getOriginalFilename();
							//UUID : 절대 겹치지 않는 숫자를 생성해줌 
		try {
		uploadFile.transferTo(new File(saveDir+savedFileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return savedFileName;
	}
}
