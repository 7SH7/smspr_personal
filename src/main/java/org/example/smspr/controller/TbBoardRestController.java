package org.example.smspr.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.smspr.dto.controller.TbBoardDto;
import org.example.smspr.service.TbBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1-1. 게시글 API 안내",
	description = "게시글 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbboard")
@RestController
public class TbBoardRestController {

	private TbBoardService tbBoardService;
	public TbBoardRestController(TbBoardService tbBoardService) {
		this.tbBoardService = tbBoardService;
	}


	@Operation(summary = "게시글 생성",
		description = "게시글 생성 컨트롤러 <br />"
			+ "@param TbBoardDto.CreateReqDto <br />"
			+ "@return HttpStatus.CREATED(201) ResponseEntity\\<TbBoardDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PostMapping("")
	public ResponseEntity<TbBoardDto.CreateResDto> create(@Valid @RequestBody TbBoardDto.CreateReqDto param){
		return ResponseEntity.status(HttpStatus.CREATED).body(tbBoardService.create(param));
	}


	@Operation(summary = "게시글 수정",
		description = "게시글 수정 컨트롤러 <br />"
			+ "@param TbBoardDto.UpdateReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbBoardDto.CreateResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@PutMapping("")
	public ResponseEntity<TbBoardDto.CreateResDto> update(@Valid @RequestBody TbBoardDto.UpdateReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbBoardService.update(param));
	}

	@Operation(summary = "게시글 상세 조회",
		description = "게시글 상세 조회 컨트롤러 <br />"
			+ "@param TbBoardDto.SelectReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbBoardDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("")
	public ResponseEntity<TbBoardDto.SelectResDto> detail(@Valid TbBoardDto.SelectReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbBoardService.detail(param));
	}
	@Operation(summary = "게시글 목록 전체 조회",
		description = "게시글 목록 전체 조회 컨트롤러 <br />"
			+ "@param TbBoardDto.ListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbBoardDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/list")
	public ResponseEntity<List<TbBoardDto.SelectResDto>> list(@Valid TbBoardDto.ListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbBoardService.list(param));
	}

	@Operation(summary = "게시글 목록 페이지 조회",
		description = "게시글 목록 페이지 조회 컨트롤러 <br />"
			+ "@param TbBoardDto.PagedListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbBoardDto.PagedListResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/plist")
	public ResponseEntity<TbBoardDto.PagedListResDto> plist(@Valid TbBoardDto.PagedListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbBoardService.pagedList(param));
	}
	@Operation(summary = "게시글 목록 스크롤 조회",
		description = "게시글 목록 스크롤 조회 컨트롤러 <br />"
			+ "@param TbBoardDto.MoreListReqDto <br />"
			+ "@return HttpStatus.OK(200) ResponseEntity\\<TbBoardDto.SelectResDto\\> <br />"
			+ "@exception 필수 파라미터 누락하였을 때 등 <br />"
	)
	@GetMapping("/mlist")
	public ResponseEntity<List<TbBoardDto.SelectResDto>> mlist(@Valid TbBoardDto.ScrollListReqDto param){
		return ResponseEntity.status(HttpStatus.OK).body(tbBoardService.scrollList(param));
	}

}
