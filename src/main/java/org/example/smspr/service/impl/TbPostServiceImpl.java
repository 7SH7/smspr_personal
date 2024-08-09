package org.example.smspr.service.impl;

import org.example.smspr.dto.controller.TbPostDto;
import org.example.smspr.entity.TbPost;
import org.example.smspr.mapper.TbPostMapper;
import org.example.smspr.repository.TbPostRepository;
import org.example.smspr.service.TbPostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbPostServiceImpl implements TbPostService {

	private TbPostRepository tbPostRepository;
	private TbPostMapper tbPostMapper;

	public TbPostServiceImpl(TbPostRepository tbPostRepository, TbPostMapper tbPostMapper) {
		this.tbPostRepository = tbPostRepository;
		this.tbPostMapper = tbPostMapper;
	}

	@Override
	public TbPostDto.CreateResDto create(TbPostDto.CreateReqDto param) {
		TbPost tbPost = tbPostRepository.save(param.toEntity());
		return tbPost.toCreateResDto();
	}

	@Override
	public TbPostDto.CreateResDto update(TbPostDto.UpdateReqDto param) {
		TbPost tbPost = tbPostRepository.findById(param.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

		if(param.getTitle() != null && param.getContent() != null && param.getAuthor() != null) {
			tbPost.setTitle(param.getTitle());
			tbPost.setContent(param.getContent());
			tbPost.setAuthor(param.getAuthor());
		}
		tbPostRepository.save(tbPost);
		return tbPost.toCreateResDto();
	}

	@Override
	public TbPostDto.SelectResDto detail(TbPostDto.SelectReqDto param) {
//		TbPost tbPost = tbPostRepository.findById(param.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
//
//		TbPostDto.SelectResDto selectResDto = TbPostDto.SelectResDto.builder()
//			.id(tbPost.getId())
//			.createdAt(tbPost.getCreatedAt() + "")
//			.deleted(tbPost.getDeleted())
//			.title(tbPost.getTitle())
//			.author(tbPost.getAuthor())
//			.content(tbPost.getContent())
//			.build();
//
//		return selectResDto;

		// TbPostMapper 사용
		TbPostDto.SelectResDto selectResDto = tbPostMapper.detail(param);
		if(selectResDto == null) {
			throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
		}
		return selectResDto;
	}

	@Override
	public List<TbPostDto.SelectResDto> list(TbPostDto.ListReqDto param) {

		List<TbPostDto.SelectResDto> list = tbPostMapper.list(param);
		List<TbPostDto.SelectResDto> selectResDtoList = new ArrayList<>();

		for(TbPostDto.SelectResDto each : list){
			selectResDtoList.add(detail(TbPostDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		return selectResDtoList;
	}


	@Override
	public TbPostDto.PagedListResDto pagedList(TbPostDto.PagedListReqDto param){

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
		int listsize = tbPostMapper.pagedListCount(param);
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

		List<TbPostDto.SelectResDto> list = tbPostMapper.pagedList(param);
		List<TbPostDto.SelectResDto> newList = new ArrayList<>();
		for(TbPostDto.SelectResDto each : list){
			newList.add(detail(TbPostDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		TbPostDto.PagedListResDto returnVal =
			TbPostDto.PagedListResDto.builder()
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
	public List<TbPostDto.SelectResDto> scrollList(TbPostDto.ScrollListReqDto param) {
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

		List<TbPostDto.SelectResDto> list = tbPostMapper.scrollList(param);
		List<TbPostDto.SelectResDto> newList = new ArrayList<>();

		for(TbPostDto.SelectResDto each : list){
			newList.add(detail(TbPostDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		return newList;
	}
}

