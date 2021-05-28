package com.project.test;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
	@Autowired
	NaverService naverService;
	@Autowired
	HHserviceImpl hhService;
	
	@RequestMapping("/postupload")
	public String uploadform() {
		return "/main/postupload";
	}
	
	@RequestMapping(value="/saveImage", method=RequestMethod.POST)
	@ResponseBody
	public String saveImage(MultipartFile file) throws IOException{
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		//서버 저장 경로 설정
		String savePath="c:/upload/";
		//저장할 경로와 파일 이름 완성
		File savefile = new File(savePath + filename);
		//서버 저장
		file.transferTo(savefile);
		return "{\"data\":\"저장했습니다!\"}";
	}
	@RequestMapping(value="/saveData", method=RequestMethod.POST)
	@ResponseBody
	public String saveData(String id, String content, String image, String hashtag) {
		System.out.println(id + "|"+ content + "|"+ image + "|"+ hashtag);
		PostVO pvo = new PostVO();
		pvo.setId(id);
		pvo.setContents(content);
		pvo.setImagepath(image);
		pvo.setHashtag(hashtag);
		hhService.insertPostData(pvo);
		return "{\"data\":\"저장완료\"}";
	}
	
	
	@RequestMapping(value="/getODjson", method=RequestMethod.POST)
	@ResponseBody
	public String uploadresult(MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		//서버 저장 경로 설정
		String savePath="c:/upload/";
		//저장할 경로와 파일 이름 완성
		File savefile = new File(savePath + filename);
		//서버 저장
		file.transferTo(savefile);
		
		String odResult = naverService.getObjectDetectionService(filename);
		String cfrResult = naverService.getCFRService(filename);
		System.out.println(filename +":" + odResult);
		System.out.println(cfrResult);
		
		return filename +"|" + odResult + "|" + cfrResult; 
	}
	
	@RequestMapping("/")
	public String main() {
		return "main/main";
	}

	@RequestMapping("/login")
	public String login() {
		return "login/main";
	}

	@RequestMapping("/login/signup")
	public String signup() {
		return "login/signup";
	}
	
	
	@RequestMapping("/profile")
	public String profile() {
		return "profile/main";
	}


	// 중복 방지를 위해 블록처리
//	@RequestMapping("/search")
//	public String search() {
//		return "search/main";
//	}

}
