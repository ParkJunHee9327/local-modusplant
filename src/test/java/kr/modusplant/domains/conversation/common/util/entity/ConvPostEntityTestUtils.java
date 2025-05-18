package kr.modusplant.domains.conversation.common.util.entity;

import kr.modusplant.domains.conversation.persistence.entity.ConvPostEntity;

public interface ConvPostEntityTestUtils {
    default ConvPostEntity createConvPostEntity() {
        return ConvPostEntity.builder()
                .ulid("01H5Z7XQ3W4F9K2G1V8R6T0Y5P")
                .build();
    }
}
