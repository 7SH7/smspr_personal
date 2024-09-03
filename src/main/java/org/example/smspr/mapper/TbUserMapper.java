package org.example.smspr.mapper;

import java.util.List;
import org.example.smspr.dto.controller.TbUserDto;

public interface TbUserMapper {

	TbUserDto.SelectResDto login(TbUserDto.LoginReqDto param);

	TbUserDto.SelectResDto detail(TbUserDto.SelectReqDto param);

	List<TbUserDto.SelectResDto> list(TbUserDto.ListReqDto param);

	List<TbUserDto.SelectResDto> scrollList(TbUserDto.ScrollListReqDto param);

	List<TbUserDto.SelectResDto> pagedList(TbUserDto.PagedListReqDto param);

	int pagedListCount(TbUserDto.PagedListReqDto param);

}
