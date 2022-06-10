package com.example.jpa_test.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // entity 간의 상속시 컬럼으로 인식하게 해줌
@EntityListeners(AuditingEntityListener.class)  // 변경감지, LifeCycle 관련된 이벤트들을 자동감지 하도록 설정
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;
}
