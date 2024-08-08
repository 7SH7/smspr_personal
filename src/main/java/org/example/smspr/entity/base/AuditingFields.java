package org.example.smspr.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mariadb.jdbc.internal.failover.impl.AuroraListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class) // AuditingEntityListener.class는 커스텀 리스너 클래스를 지정하여, 엔티티의 상태 변화(예: 저장, 삭제 등)에 따라 특정 작업을 수행할 수 있도록 합니다.
@MappedSuperclass // 상위 클래스를 정의하여 하위 클래스에서 상속받을 수 있는 매핑 정보를 제공합니다.
public abstract class AuditingFields {

	@Id
	private String id;

	@Column(nullable = false) //이거는 테이블 컬럼에 속성을 주기 위함 입니다!! 낫 널!!!!
	@Setter
	protected String deleted;

	@Column(nullable = true)
	@Setter
	protected String process;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(nullable = false, updatable = false)
//	LocalDateTime = LocalDate + LocalTime
	protected LocalDateTime createdAt; // 생성일시

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@LastModifiedDate
	@Column(nullable = false)
	protected LocalDateTime modifiedAt; // 수정일시

	@PrePersist // PrePersist는 엔티티가 DB에 저장되기 전에 실행되는 메서드입니다.
	public void onPrePersist() {
		this.id = UUID.randomUUID().toString().replace("-", "");
		this.deleted = "N";
	}

}
