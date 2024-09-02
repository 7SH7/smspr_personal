package org.example.smspr.mapper;

import java.util.List;
import org.example.smspr.dto.controller.TbBoardDto;

public interface TbBoardMapper {
	TbBoardDto.SelectResDto detail(TbBoardDto.SelectReqDto param);

	List<TbBoardDto.SelectResDto> list(TbBoardDto.ListReqDto param);

	List<TbBoardDto.SelectResDto> pagedList(TbBoardDto.PagedListReqDto param);

	int pagedListCount(TbBoardDto.PagedListReqDto param);

	List<TbBoardDto.SelectResDto> scrollList(TbBoardDto.ScrollListReqDto param);

}
