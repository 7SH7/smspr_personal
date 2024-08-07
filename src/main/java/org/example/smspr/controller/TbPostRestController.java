package org.example.smspr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.smspr.dto.controller.TbPostDto;
import org.example.smspr.service.TbPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1-1. 게시글 API 안내",
	description = "게시글 관련 기능 정의한 RestController.")
@RestController
@RequestMapping("/api/tbpost")
public class TbPostRestController {

	private TbPostService tbPostService;
	public TbPostRestController(TbPostService tbPostService) {
		this.tbPostService = tbPostService;
	}

	@Operation(summary = "게시글 생성",
		description = "게시글 생성 컨트롤러 <br />"
			+ "@param TbPostDto.CreateReqDto <br />"
			+ "@return HttpStatus.CREATED(201) ResponseEntity\\<TbPostDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PostMapping("")
	public ResponseEntity<TbPostDto.CreateResDto> create(@Valid @RequestBody TbPostDto.CreateReqDto param){
		return ResponseEntity.status(HttpStatus.CREATED).body(tbPostService.create(param));
	}

	
	@Operation(summary = "게시글 수정",
		description = "게시글 수정 컨트롤러 <br />"
			+ "@param TbPostDto.UpdateReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbPostDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PutMapping("")
	public ResponseEntity<TbPostDto.CreateResDto> update(@Valid @RequestBody TbPostDto.UpdateReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbPostService.update(param));
	}

	
	@Operation(summary = "게시글 상세 조회",
		description = "게시글 상세 조회 컨트롤러 <br />"
			+ "@param TbPostDto.SelectReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbPostDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("")
	public ResponseEntity<TbPostDto.SelectResDto> detail(@Valid TbPostDto.SelectReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbPostService.detail(param));
	}
	

	@Operation(summary = "게시글 목록 전체 조회",
		description = "게시글 목록 전체 조회 컨트롤러 <br />"
			+ "@param TbPostDto.ListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbPostDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/list")
	public ResponseEntity<List<TbPostDto.SelectResDto>> list(@Valid TbPostDto.ListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbPostService.list(param));
	}

}