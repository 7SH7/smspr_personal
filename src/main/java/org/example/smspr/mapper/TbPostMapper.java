package org.example.smspr.mapper;

import org.example.smspr.dto.controller.TbPostDto;

import java.util.List;

public interface TbPostMapper {
	TbPostDto.SelectResDto detail(TbPostDto.SelectReqDto param);
	List<TbPostDto.SelectResDto> list(TbPostDto.ListReqDto param);
}