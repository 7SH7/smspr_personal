package org.example.smspr.service;

import java.util.List;
import org.example.smspr.dto.controller.TbBoardDto;

public interface TbBoardService {

	TbBoardDto.CreateResDto create(TbBoardDto.CreateReqDto param);

	TbBoardDto.CreateResDto update(TbBoardDto.UpdateReqDto param);

	TbBoardDto.SelectResDto detail(TbBoardDto.SelectReqDto param);

	List<TbBoardDto.SelectResDto> list(TbBoardDto.ListReqDto param);

	TbBoardDto.PagedListResDto pagedList(TbBoardDto.PagedListReqDto param);

	List<TbBoardDto.SelectResDto> scrollList(TbBoardDto.ScrollListReqDto param);
}
