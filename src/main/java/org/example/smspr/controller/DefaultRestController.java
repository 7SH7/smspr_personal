package org.example.smspr.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.smspr.dto.DefaultDto;
import org.example.smspr.util.FileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/default")
public class DefaultRestController {

	private final FileUpload fileUpload;

	public DefaultRestController(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	@Operation(summary = "파일업로드",
		description = "파일을 서버에 업로드(일반) \n"
			+ "@param MultipartFile multipartFile \n"
			+ "@return HttpStatus.CREATED(201) ResponseEntity\\<String\\> \n"
			+ "@exception \n"
	)
	@PostMapping("/uploadFile")
	public ResponseEntity<DefaultDto.FileResDto> uploadFile(@Valid @RequestParam("file")MultipartFile files, HttpServletRequest request){
		// HttpServletRequest request : 파일을 저장할 경로를 얻기 위해 사용
		// (URL, HTTP 메서드(예: GET, POST), 헤더, 파라미터, 세션 정보 등을 조회 가능)

		DefaultDto.FileResDto urlResDto = null;
		try {
			//urlResDto = DefaultDto.FileResDto.builder().url(fileUpload.s3(file)).build();   // <- 얘는 S3에 저장하는 거!
			urlResDto = DefaultDto.FileResDto.builder().url(fileUpload.local(files, request)).build();  // <- 얘는 로컬에 저장하는 거!
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(urlResDto);
	}




}

