package kr.modusplant.domains.conversation.domain.model;

import lombok.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@Builder(access = AccessLevel.PUBLIC)
public class ConvPost {
    // TODO: ConvCommentEntity에 필요해서 임시로 만들었음.
    private final String ulid;

    public static class ConvPostBuilder {
        private String ulid;

        public ConvPost.ConvPostBuilder convPost(ConvPost convPost) {
            this.ulid = convPost.getUlid();
            return this;
        }

        public ConvPost build() {
            return new ConvPost(this.ulid);
        }
    }
}
