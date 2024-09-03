package org.example.smspr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.example.smspr.dto.controller.TbUserDto;
import org.example.smspr.service.TbUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "0-1. 사용자 API 안내",
	description = "사용자 관련 기능 정의한 RestController.")
@RestController
@RequestMapping("/api/tbuser")
public class TbUserRestController {

	private TbUserService tbUserService;
	public TbUserRestController(TbUserService tbUserService) {
		this.tbUserService = tbUserService;
	}

	@Operation(summary = "사용자 로그인",
		description = "사용자 로그인 컨트롤러 <br />"
			+ "@param TbuserDto.LoginReqDto <br />"
			+ "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PostMapping("/login")
	public ResponseEntity<TbUserDto.CreateResDto> login(@Valid @RequestBody TbUserDto.LoginReqDto param){
		return ResponseEntity.status(HttpStatus.CREATED).body(tbUserService.login(param));
	}

	@Operation(summary = "사용자 가입",
		description = "사용자 가입 컨트롤러 <br />"
			+ "@param TbuserDto.SignupReqDto <br />"
			+ "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PostMapping("/signup")
	public ResponseEntity<TbUserDto.CreateResDto> signup(@Valid @RequestBody TbUserDto.SignupReqDto param){
		return ResponseEntity.status(HttpStatus.CREATED).body(tbUserService.signup(param));
	}

	//

	@Operation(summary = "사용자 생성",
		description = "사용자 생성 컨트롤러 <br />"
			+ "@param TbuserDto.CreateReqDto <br />"
			+ "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PostMapping("")
	public ResponseEntity<TbUserDto.CreateResDto> create(@Valid @RequestBody TbUserDto.CreateReqDto param){
		return ResponseEntity.status(HttpStatus.CREATED).body(tbUserService.create(param));
	}


	@Operation(summary = "사용자 수정",
		description = "사용자 수정 컨트롤러 <br />"
			+ "@param TbuserDto.UpdateReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PutMapping("")
	public ResponseEntity<TbUserDto.CreateResDto> update(@Valid @RequestBody TbUserDto.UpdateReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbUserService.update(param));
	}


	@Operation(summary = "사용자 상세 조회",
		description = "사용자 상세 조회 컨트롤러 <br />"
			+ "@param TbuserDto.SelectReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("")
	public ResponseEntity<TbUserDto.SelectResDto> detail(@Valid TbUserDto.SelectReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbUserService.detail(param));
	}


	@Operation(summary = "사용자 목록 전체 조회",
		description = "사용자 목록 전체 조회 컨트롤러 <br />"
			+ "@param TbuserDto.ListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/list")
	public ResponseEntity<List<TbUserDto.SelectResDto>> list(@Valid TbUserDto.ListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbUserService.list(param));
	}

	@Operation(summary = "사용자 목록 페이지 조회",
		description = "사용자 목록 페이지 조회 컨트롤러 <br />"
			+ "@param TbuserDto.PagedListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.PagedListResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/plist")
	public ResponseEntity<TbUserDto.PagedListResDto> plist(@Valid TbUserDto.PagedListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbUserService.pagedList(param));
	}

	@Operation(summary = "사용자 목록 스크롤 조회",
		description = "사용자 목록 스크롤 조회 컨트롤러 <br />"
			+ "@param TbuserDto.MoreListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/mlist")
	public ResponseEntity<List<TbUserDto.SelectResDto>> mlist(@Valid TbUserDto.ScrollListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbUserService.scrollList(param));
	}

}