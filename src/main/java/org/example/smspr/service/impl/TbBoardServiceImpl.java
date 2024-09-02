package org.example.smspr.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.example.smspr.dto.controller.TbBoardDto;
import org.example.smspr.entity.TbBoard;
import org.example.smspr.mapper.TbBoardMapper;
import org.example.smspr.repository.TbBoardRepository;
import org.example.smspr.service.TbBoardService;
import org.springframework.stereotype.Service;

@Service
public class TbBoardServiceImpl implements TbBoardService {

	private TbBoardRepository tbBoardRepository;
	private TbBoardMapper tbBoardMapper;

	public TbBoardServiceImpl(TbBoardRepository tbBoardRepository, TbBoardMapper tbBoardMapper) {
		this.tbBoardRepository = tbBoardRepository;
		this.tbBoardMapper = tbBoardMapper;
	}

	@Override
	public TbBoardDto.CreateResDto create(TbBoardDto.CreateReqDto param) {
		TbBoard TbBoard = tbBoardRepository.save(param.toEntity());
		return TbBoard.toCreateResDto();
	}

	@Override
	public TbBoardDto.CreateResDto update(TbBoardDto.UpdateReqDto param) {
		TbBoard TbBoard = tbBoardRepository.findById(param.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

		if(param.getTitle() != null && param.getContent() != null && param.getAuthor() != null) {
			TbBoard.setTitle(param.getTitle());
			TbBoard.setContent(param.getContent());
			TbBoard.setAuthor(param.getAuthor());
		}
		tbBoardRepository.save(TbBoard);
		return TbBoard.toCreateResDto();
	}

	@Override
	public TbBoardDto.SelectResDto detail(TbBoardDto.SelectReqDto param) {
//		TbBoard TbBoard = TbBoardRepository.findById(param.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
//
//		TbBoardDto.SelectResDto selectResDto = TbBoardDto.SelectResDto.builder()
//			.id(TbBoard.getId())
//			.createdAt(TbBoard.getCreatedAt() + "")
//			.deleted(TbBoard.getDeleted())
//			.title(TbBoard.getTitle())
//			.author(TbBoard.getAuthor())
//			.content(TbBoard.getContent())
//			.build();
//
//		return selectResDto;

		// TbBoardMapper 사용
		TbBoardDto.SelectResDto selectResDto = tbBoardMapper.detail(param);
		if(selectResDto == null) {
			throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
		}
		return selectResDto;
	}

	@Override
	public List<TbBoardDto.SelectResDto> list(TbBoardDto.ListReqDto param) {

		List<TbBoardDto.SelectResDto> list = tbBoardMapper.list(param);
		List<TbBoardDto.SelectResDto> selectResDtoList = new ArrayList<>();

		for(TbBoardDto.SelectResDto each : list){
			selectResDtoList.add(detail(TbBoardDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		return selectResDtoList;
	}


	@Override
	public TbBoardDto.PagedListResDto pagedList(TbBoardDto.PagedListReqDto param){

		String orderby = param.getOrderby();
		if(orderby == null || orderby.isEmpty()){
			orderby = "created_at";
		}
		String orderway = param.getOrderway();
		if(orderway == null || orderway.isEmpty()){
			orderway = "desc";
		}
		Integer perpage = param.getPerpage();
		if(perpage == null || perpage < 1){
			//한번에 조회할 글 갯수
			perpage = 10;
		}
		Integer callpage = param.getCallpage();
		if(callpage == null){
			//호출하는 페이지
			callpage = 1;
		}
		if(callpage < 1){
			callpage = 1;
		}

		//offset 을 계산하기 위해서는 전체 글 갯수가 필요합니다!
		int listsize = tbBoardMapper.pagedListCount(param);
        /*
        총 글 등록 수 : 127 개
        총 페이지 수 : 13개 (10개씩 보는 기준)
        내가 2페이지를 호출한다면 몇번째 부터 보면 될까요?! 11번째 => 10(offset)
        */
		int pagesize = listsize / perpage;
		if(listsize % perpage > 0){
			pagesize++;
		}
		if(callpage > pagesize){
			callpage = pagesize;
		}
		int offset = (callpage - 1) * perpage;
		param.setOrderby(orderby);
		param.setOrderway(orderway);
		param.setOffset(offset);
		param.setPerpage(perpage);
		//1페이지일때 0
		//2페이지 일때 10

		List<TbBoardDto.SelectResDto> list = tbBoardMapper.pagedList(param);
		List<TbBoardDto.SelectResDto> newList = new ArrayList<>();
		for(TbBoardDto.SelectResDto each : list){
			newList.add(detail(TbBoardDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		TbBoardDto.PagedListResDto returnVal =
			TbBoardDto.PagedListResDto.builder()
				.callpage(callpage)
				.perpage(perpage)
				.orderby(orderby)
				.orderway(orderway)
				.listsize(listsize)
				.pagesize(pagesize)
				.list(newList)
				.build();

		return returnVal;
	}

	@Override
	public List<TbBoardDto.SelectResDto> scrollList(TbBoardDto.ScrollListReqDto param) {
		String orderby = param.getOrderby();
		if(orderby == null || orderby.isEmpty()){
			orderby = "created_at";
			param.setOrderby(orderby);
		}
		String orderway = param.getOrderway();
		if(orderway == null || orderway.isEmpty()){
			orderway = "desc";
			param.setOrderway(orderway);
		}
//		String cursor = param.getCursor(); <- 구지 필요없음
//		if(cursor == null || cursor.isEmpty()){
//			if("created_at".equals(orderby) && "desc".equals(orderway)){
//				cursor = "9999-12-31 23:59:59.999999";
//				param.setCursor(cursor);
//			}
//		}
		Integer perpage = param.getPerpage();
		if(perpage == null || perpage < 1){
			//한번에 조회할 글 갯수
			perpage = 10;
			param.setPerpage(perpage);
		}

		List<TbBoardDto.SelectResDto> list = tbBoardMapper.scrollList(param);
		List<TbBoardDto.SelectResDto> newList = new ArrayList<>();

		for(TbBoardDto.SelectResDto each : list){
			newList.add(detail(TbBoardDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		return newList;
	}
}

