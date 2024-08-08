package org.example.smspr.service;

import org.example.smspr.dto.controller.TbPostDto;

import java.util.List;

public interface TbPostService {

	TbPostDto.CreateResDto create(TbPostDto.CreateReqDto param);

	TbPostDto.CreateResDto update(TbPostDto.UpdateReqDto param);

	TbPostDto.SelectResDto detail(TbPostDto.SelectReqDto param);

	List<TbPostDto.SelectResDto> list(TbPostDto.ListReqDto param);

	TbPostDto.PagedListResDto pagedList(TbPostDto.PagedListReqDto param);

	List<TbPostDto.SelectResDto> scrollList(TbPostDto.ScrollListReqDto param);
}
