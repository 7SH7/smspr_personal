package org.example.smspr.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.example.smspr.dto.controller.TbUserDto;
import org.example.smspr.entity.TbUser;
import org.example.smspr.mapper.TbUserMapper;
import org.example.smspr.repository.TbUserRepository;
import org.example.smspr.service.TbUserService;
import org.springframework.stereotype.Service;

@Service
public class TbUserServiceImpl implements TbUserService {

	private TbUserRepository tbUserRepository;
	private TbUserMapper tbUserMapper;

	public TbUserServiceImpl(TbUserRepository tbUserRepository, TbUserMapper tbUserMapper) {
		this.tbUserRepository = tbUserRepository;
		this.tbUserMapper = tbUserMapper;
	}

	@Override
	public TbUserDto.CreateResDto login(TbUserDto.LoginReqDto param) {
		TbUser tbuser = tbUserRepository.findByUsernameAndPassword(param.getUsername(), param.getPassword());
		if(tbuser == null){ return TbUserDto.CreateResDto.builder().id("not matched").build(); }

		return TbUserDto.CreateResDto.builder().id(tbuser.getId()).build();
	}

	@Override
	public TbUserDto.CreateResDto signup(TbUserDto.SignupReqDto param) {
		TbUserDto.CreateReqDto newParam = TbUserDto.CreateReqDto.builder().username(param.getUsername()).password(param.getPassword()).build();
		return tbUserRepository.save(newParam.toEntity()).toCreateResDto();
	}

	@Override
	public TbUserDto.CreateResDto create(TbUserDto.CreateReqDto param) {
		return tbUserRepository.save(param.toEntity()).toCreateResDto();
	}

	@Override
	public TbUserDto.CreateResDto update(TbUserDto.UpdateReqDto param) {
		TbUser tbUser = tbUserRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException("no data"));

		if(param.getName() != null){
			tbUser.setName(param.getName());
		}
		if(param.getNick() != null){
			tbUser.setNick(param.getNick());
		}
		if(param.getPhone() != null){
			tbUser.setPhone(param.getPhone());
		}
		if(param.getGender() != null){
			tbUser.setGender(param.getGender());
		}
		if(param.getContent() != null){
			tbUser.setContent(param.getContent());
		}
		if(param.getImg() != null){
			tbUser.setImg(param.getImg());
		}
		tbUserRepository.save(tbUser);
		return tbUser.toCreateResDto();
	}

	@Override
	public TbUserDto.SelectResDto detail(TbUserDto.SelectReqDto param) {
		TbUserDto.SelectResDto selectResDto = tbUserMapper.detail(param);
		if(selectResDto == null) {
			throw new RuntimeException("no data");
		}
		return selectResDto;
	}

	@Override
	public List<TbUserDto.SelectResDto> list(TbUserDto.ListReqDto param) {

		List<TbUserDto.SelectResDto> list = tbUserMapper.list(param);
		List<TbUserDto.SelectResDto> selectResDtoList = new ArrayList<>();

		for(TbUserDto.SelectResDto each : list){
			selectResDtoList.add(detail(TbUserDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		return selectResDtoList;
	}


	@Override
	public TbUserDto.PagedListResDto pagedList(TbUserDto.PagedListReqDto param){

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
		int listsize = tbUserMapper.pagedListCount(param);
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

		List<TbUserDto.SelectResDto> list = tbUserMapper.pagedList(param);
		List<TbUserDto.SelectResDto> newList = new ArrayList<>();
		for(TbUserDto.SelectResDto each : list){
			newList.add(detail(TbUserDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		TbUserDto.PagedListResDto returnVal =
			TbUserDto.PagedListResDto.builder()
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
	public List<TbUserDto.SelectResDto> scrollList(TbUserDto.ScrollListReqDto param) {
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
		Integer perpage = param.getPerpage();
		if(perpage == null || perpage < 1){
			//한번에 조회할 글 갯수
			perpage = 10;
			param.setPerpage(perpage);
		}

		List<TbUserDto.SelectResDto> list = tbUserMapper.scrollList(param);
		List<TbUserDto.SelectResDto> newList = new ArrayList<>();

		for(TbUserDto.SelectResDto each : list){
			newList.add(detail(TbUserDto.SelectReqDto.builder().id(each.getId()).build()));
		}

		return newList;
	}
}

