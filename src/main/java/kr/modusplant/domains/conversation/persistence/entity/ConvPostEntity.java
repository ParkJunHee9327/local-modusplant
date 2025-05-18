package kr.modusplant.domains.conversation.persistence.entity;

import lombok.*;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@Builder(access = AccessLevel.PUBLIC)
public class ConvPostEntity {
    // TODO: ConvCommentEntity에 필요해서 임시로 만들었음.
    private final String ulid;

    public static final class ConvPostEntityBuilder {
        private String ulid;

        public ConvPostEntity.ConvPostEntityBuilder ulid(final String ulid) {
            this.ulid = ulid;
            return this;
        }

        public ConvPostEntity build() {
            return new ConvPostEntity(this.ulid);
        }
    }
}
