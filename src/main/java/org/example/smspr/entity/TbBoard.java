package org.example.smspr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smspr.dto.controller.TbBoardDto;
import org.example.smspr.entity.base.AuditingFields;

@Entity
@Getter
@Table(indexes = {
	@Index(columnList = "deleted"),
	@Index(columnList = "process"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "modifiedAt")
//	@Index(columnList = "title")
})  // 다중 index 설정
public class TbBoard extends AuditingFields {

	@Setter @Column(nullable = false, length = 400) private String title;
	@Setter @Column(nullable = false, length = 400) private String author;
	// @Lob은 대용량 데이터가 들어옴을 암시해주는 annotation (필수 X)
	@Setter @Column(nullable = true, length = 100000) @Lob private String content;

	protected TbBoard() {}

	private TbBoard(String title, String author, String content) {
		this.title = title;
		this.author = author;
		this.content = content;
	}

	public static TbBoard from(String title, String author, String content) {
		return new TbBoard(title, author, content);
	}

	public TbBoardDto.CreateResDto toCreateResDto() {
		return TbBoardDto.CreateResDto.builder().id(this.getId()).build();
	}

}
