package org.example.smspr.service;

import java.util.List;
import org.example.smspr.dto.controller.TbUserDto;

public interface TbUserService {

	TbUserDto.CreateResDto login(TbUserDto.LoginReqDto param);
	TbUserDto.CreateResDto signup(TbUserDto.SignupReqDto param);

	TbUserDto.CreateResDto create(TbUserDto.CreateReqDto param);

	TbUserDto.CreateResDto update(TbUserDto.UpdateReqDto param);

	TbUserDto.SelectResDto detail(TbUserDto.SelectReqDto param);

	List<TbUserDto.SelectResDto> list(TbUserDto.ListReqDto param);

	TbUserDto.PagedListResDto pagedList(TbUserDto.PagedListReqDto param);

	List<TbUserDto.SelectResDto> scrollList(TbUserDto.ScrollListReqDto param);
}
