package org.example.smspr.controller.page;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.example.smspr.util.FileUpload;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

@RequestMapping("")
@Controller
public class DefaultController {

	private Logger logger;

	@GetMapping("index")
	public String index() {
		return "index";
	}

	@GetMapping("/agree")
	public String agree() {
		return "agree";
	}

	@ResponseBody   // @RestController = @ResponseBody + @Controller 의미
	@RequestMapping(value = "/uploadfile/{file_name:.+}", method = {RequestMethod.GET, RequestMethod.POST})  // @GetMapping + @PostMapping 과 동일
	public byte[] getImage(@PathVariable("file_name") String file_name, HttpServletRequest request) throws Exception{
		// file_name이랑 현재 파일 위치를 알 수 있는 HttpServeletRequest를 가져와
		logger.info("file_name" + file_name);
		String root_path = FileUpload.path(request);

		logger.info("file_name" + file_name);
		byte[] return_byte = null;

		// 일단 file을 열 준비를 해.
		File file = new File(root_path + file_name);

		// 해당 이미지를 byte[] 형태로 변환 준비 및 실행
		InputStream in = null;
		try {
			in = new FileInputStream(file); // file input stream 불러오고..
			return_byte = IOUtils.toByteArray(in);  // IOUtils 써서 byte array로 바꿔줘.
		} catch (FileNotFoundException e){
		} catch (IOException e){
		} finally {
			if(in != null){
				try{
					in.close();     // 닫는 것까지..
				} catch (Exception e){
				}
			}
		}
		return return_byte;
	}

	@ResponseBody
	@RequestMapping(value = "/download/{file_name:.+}", method = RequestMethod.GET)
	public void download(@PathVariable("file_name") String file_name, @RequestParam Map<String, Object> map,  HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 파일 저장 path
		String root_path = FileUpload.path(request);

		// 파일 불러오기
		File file = new File(root_path + file_name);

		// 파일 확장자를 보고 해당 파일의 형식을 유추하여 적절한 MIME 타입을 반환 (.jpg -> /jpeg라는 type 반환)
		String mimeType = URLConnection.guessContentTypeFromName(file_name);
		if(mimeType == null) mimeType = "application/octet-stream";

		// download니까, 현재 올라가 있는 type과 동일한 type을 배치
		response.setContentType(mimeType);
		response.setContentLength((int) file.length());  // 이름 길이도 동일하게.

		// URL 인코딩은 안전하게 URL을 표현하기 위한 방법으로, 특수 문자를 % 기호와 함께 16진수로 변환합니다.
		// 예를 들어, 파일 이름이 my file.txt라면, 인코딩 후 my%20file.txt와 같이 변환
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(file.getName(), "utf-8") + "\"");

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));   // inputstream 얻어서..
		FileCopyUtils.copy(inputStream, response.getOutputStream());                    // outputstream으로 보내줘.
	}

}
