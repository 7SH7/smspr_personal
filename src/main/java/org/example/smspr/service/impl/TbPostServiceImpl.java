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
}

