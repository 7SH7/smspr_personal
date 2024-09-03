package org.example.smspr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smspr.dto.controller.TbPostDto;
import org.example.smspr.dto.controller.TbUserDto;
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
public class TbUser extends AuditingFields {

	@Setter @Column(nullable = false, unique = true) private String username;
	@Setter @Column(nullable = false) private String password;
	@Setter @Column(nullable = true) private String name;
	@Setter @Column(nullable = true) private String nick;
	@Setter @Column(nullable = true) private String phone;
	@Setter @Column(nullable = true) private String gender;
	@Setter @Column(nullable = true, length=10000) @Lob private String content; // 본문
	@Setter @Column(nullable = true) private String img;

	protected TbUser(){}
	private TbUser(String username, String password, String name, String nick, String phone, String gender, String content, String img) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.nick = nick;
		this.phone = phone;
		this.gender = gender;
		this.content = content;
		this.img = img;
	}
	public static TbUser from(String username, String password, String name, String nick, String phone, String gender, String content, String img) {
		return new TbUser(username, password, name, nick, phone, gender, content, img);
	}

	public TbUserDto.CreateResDto toCreateResDto() {
		return TbUserDto.CreateResDto.builder().id(this.getId()).build();
	}
}
