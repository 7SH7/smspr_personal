package org.example.smspr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DefaultDto {

	@Builder
	@Schema
	@Getter
	@Setter
	public static class FileResDto{
		private String url;
	}

}
