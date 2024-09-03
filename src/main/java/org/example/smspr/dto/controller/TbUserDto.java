package org.example.smspr.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.*;
import org.example.smspr.entity.TbUser;

public class TbUserDto {

	@Builder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginReqDto{
		@Schema(description = "username", example="")
		@NotNull
		@NotEmpty
		@Size(max=400)
		private String username;
		@Schema(description = "password", example="")
		@NotNull
		@NotEmpty
		@Size(max=100)
		private String password;
	}

	@Builder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupReqDto{
		@Schema(description = "username", example="")
		@NotNull
		@NotEmpty
		@Size(max=400)
		private String username;
		@Schema(description = "password", example="")
		@NotNull
		@NotEmpty
		@Size(max=100)
		private String password;
	}

	@Schema
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Getter
	@Setter
	public static class CreateReqDto {

		@Schema(description = "username", example="")
		@NotNull
		@NotEmpty
		@Size(max=400)
		private String username;
		@Schema(description = "password", example="")
		@NotNull
		@NotEmpty
		@Size(max=100)
		private String password;

		public TbUser toEntity(){
			return TbUser.from(username, password, null, null, null, null, null, null);
		}
	}

	@Builder
	@Schema
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateResDto {
		private String id;
	}

	@Builder
	@Schema
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdateReqDto {

		@Schema(description = "id", example = "")
		@NotNull
		@NotEmpty
		private String id;

		@Schema(description = "name", example="")
		private String name;
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "phone", example="")
		private String phone;
		@Schema(description = "gender", example="")
		private String gender;
		@Schema(description = "content", example="")
		@Size(max=4000)
		private String content;
		@Schema(description = "img", example="")
		private String img;


	}

	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema
	public static class SelectResDto {
		private String id;

		private String deleted;
		private String process;
		private String createdAt;
		private String modifiedAt;

		@Schema(description = "username", example="")
		private String username;
		@Schema(description = "password", example="")
		private String password;
		@Schema(description = "name", example="")
		private String name;
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "phone", example="")
		private String phone;
		@Schema(description = "gender", example="")
		private String gender;
		@Schema(description = "content", example="")
		private String content;
		@Schema(description = "img", example="")
		private String img;
	}

	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema
	public static class SelectReqDto {
		@Schema(description = "id", example = "")
		@NotNull
		@NotEmpty
		private String id;
	}

	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema
	public static class ListReqDto{
	@Schema(description = "deleted", example="")
	private String deleted;
	@Schema(description = "process", example="")
	private String process;

	@Schema(description = "name", example="")
	private String name;
	@Schema(description = "nick", example="")
	private String nick;
	@Schema(description = "phone", example="")
	private String phone;
}

	@Schema
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class PagedListReqDto {

		@Schema(description = "callpage", example="")
		private Integer callpage;
		@Schema(description = "perpage", example="")
		private Integer perpage;
		@Schema(description = "orderby", example="")
		private String orderby;
		@Schema(description = "orderway", example="")
		private String orderway;

		//원래는 고객한테 받으면 안되는 정보!
		@Schema(description = "offset", example="")
		private Integer offset;

		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "title", example="")
		private String title;
		@Schema(description = "author", example="")
		private String author;

	}

	@Schema
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class PagedListResDto {

		@Schema(description = "callpage", example="")
		private Integer callpage;
		@Schema(description = "perpage", example="")
		private Integer perpage;
		@Schema(description = "orderby", example="")
		private String orderby;
		@Schema(description = "orderway", example="")
		private String orderway;

		@Schema(description = "listsize", example="")
		private Integer listsize;
		@Schema(description = "pagesize", example="")
		private Integer pagesize;

		@Schema(description = "list", example="")
		private List<SelectResDto> list;

	}

	@Schema
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class ScrollListReqDto {
		@Schema(description = "cursor", example="")
		private String cursor;
		@Schema(description = "perpage", example="")
		private Integer perpage;
		@Schema(description = "orderby", example="")
		private String orderby;
		@Schema(description = "orderway", example="")
		private String orderway;

		@Schema(description = "deleted", example="")
		private String deleted;
		@Schema(description = "process", example="")
		private String process;

		@Schema(description = "name", example="")
		private String name;
		@Schema(description = "nick", example="")
		private String nick;
		@Schema(description = "phone", example="")
		private String phone;
	}


}
