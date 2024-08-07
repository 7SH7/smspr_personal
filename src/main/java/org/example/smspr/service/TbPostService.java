package org.example.smspr.service;

import org.example.smspr.dto.controller.TbPostDto;

public interface TbPostService {

	TbPostDto.CreateResDto create(TbPostDto.CreateReqDto param);

	TbPostDto.CreateResDto update(TbPostDto.UpdateReqDto param);

	TbPostDto.SelectResDto detail(TbPostDto.SelectReqDto param);

}
