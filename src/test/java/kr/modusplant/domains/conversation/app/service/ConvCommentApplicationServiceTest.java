package kr.modusplant.domains.conversation.app.service;

import kr.modusplant.domains.conversation.app.http.response.ConvCommentResponse;
import kr.modusplant.domains.conversation.common.util.app.http.request.ConvCommentRequestTestUtils;
import kr.modusplant.domains.conversation.common.util.app.http.response.ConvCommentResponseTestUtils;
import kr.modusplant.domains.conversation.common.util.domain.ConvCommentTestUtils;
import kr.modusplant.domains.conversation.common.util.domain.ConvPostTestUtils;
import kr.modusplant.domains.conversation.common.util.entity.ConvCommentEntityTestUtils;
import kr.modusplant.domains.conversation.common.util.entity.ConvPostEntityTestUtils;
import kr.modusplant.domains.conversation.common.util.entity.compositekey.ConvCommentCompositeKeyTestUtils;
import kr.modusplant.domains.conversation.domain.service.ConvCommentValidationService;
import kr.modusplant.domains.conversation.mapper.ConvCommentAppInfraMapper;
import kr.modusplant.domains.conversation.persistence.entity.ConvCommentEntity;
import kr.modusplant.domains.conversation.persistence.entity.ConvPostEntity;
import kr.modusplant.domains.conversation.persistence.entity.compositekey.ConvCommentCompositeKey;
import kr.modusplant.domains.conversation.persistence.repository.ConvCommentRepository;
import kr.modusplant.domains.member.persistence.entity.SiteMemberEntity;
import kr.modusplant.domains.member.persistence.entity.SiteMemberTermEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

public class ConvCommentApplicationServiceTest
        implements ConvCommentRequestTestUtils, ConvCommentResponseTestUtils,
        ConvCommentTestUtils, ConvPostTestUtils,
        ConvCommentEntityTestUtils, ConvPostEntityTestUtils,
        ConvCommentCompositeKeyTestUtils {
    
    private final ConvCommentApplicationService commentApplicationService;
    private final ConvCommentValidationService commentValidationService;
    private final ConvCommentRepository commentRepository;
    private final ConvCommentAppInfraMapper commentAppInfraMapper;

    @Autowired
    ConvCommentApplicationServiceTest(
            ConvCommentApplicationService commentApplicationService,
            ConvCommentValidationService commentValidationService,
            ConvCommentRepository commentRepository,
            ConvCommentAppInfraMapper commentAppInfraMapper
    ) {
        this.commentApplicationService = commentApplicationService;
        this.commentValidationService = commentValidationService;
        this.commentRepository = commentRepository;
        this.commentAppInfraMapper = commentAppInfraMapper;
    }

    @BeforeEach
    void setUp() {
        ConvCommentEntity commentEntity = createConvCommentEntity();
        ConvPostEntity postEntity = createConvPostEntity();
        ConvCommentCompositeKey compositeKey = createCompositeKeyWithAllArgs();
        UUID authMemberUuid = commentEntity.getAuthMemberUuid();
        UUID createMemberUuid = commentEntity.getCreateMemberUuid();

        given(commentRepository.findById(compositeKey)).willReturn(Optional.of(commentEntity));
        given(commentRepository.findBypostUlid(anyString())).willReturn(List.of(commentEntity));
        given(commentRepository.findByAuthMemberUuid(authMemberUuid)).willReturn(List.of(commentEntity));
        given(commentRepository.findByCreateMemberUuid(createMemberUuid)).willReturn(List.of(commentEntity));
        given(commentRepository.findByCreateMemberUuid(createMemberUuid)).willReturn(List.of(commentEntity));
        given(commentRepository.findByContent(anyString())).willReturn(List.of(commentEntity));
    }

    @DisplayName("게시글 UUID로 대화 댓글 얻기")
    @Test
    void getByPostUlid_givenValidRequest_thenReturnConvCommentResponseList() {
        // when
        // TODO: post 서비스에 post를 넣는 과정이 먼저 필요함. post 서비스가 구현되면 받아올 것.
        ConvCommentResponse commentResponse = commentApplicationService.insert(convCommentInsertRequest);

        // then
        assertThat(commentApplicationService.getByUuid(uuid).orElseThrow()).isEqualTo(commentResponse);
    }

    @DisplayName("member로 회원 약관 얻기")
    @Test
    void getByMemberTest() {
//        SiteMemberTermEntity memberTermEntity = createMemberTermUserEntityWithUuid();
//        SiteMemberEntity memberEntity = memberTermEntity.getMember();
//        UUID uuid = memberEntity.getUuid();

//        given(memberRepository.findByUuid(uuid)).willReturn(Optional.of(memberEntity));
//        given(memberRepository.save(createMemberBasicUserEntity())).willReturn(memberEntity);
//        given(memberTermRepository.findByUuid(uuid)).willReturn(Optional.empty()).willReturn(Optional.of(memberTermEntity));
//        given(memberTermRepository.save(memberTermEntity)).willReturn(memberTermEntity);

        // when
        ConvCommentResponse commentResponse = commentApplicationService.insert(insertRequest);

        // then
        assertThat(commentApplicationService.getByMember(memberEntity).orElseThrow()).isEqualTo(commentResponse);
    }

    @DisplayName("agreedTermsOfUseVersion으로 회원 약관 얻기")
    @Test
    void getByAgreedTermsOfUseVersionTest() {
        // given
//        SiteMemberTermEntity memberTermEntity = createMemberTermUserEntityWithUuid();
//        SiteMemberEntity memberEntity = memberTermEntity.getMember();

        // when
        ConvCommentResponse commentResponse = commentApplicationService.insert(insertRequest);

        // then
        assertThat(commentApplicationService.getByAgreedTermsOfUseVersion(memberTermEntity.getAgreedTermsOfUseVersion()).getFirst()).isEqualTo(commentResponse);
    }

    @DisplayName("agreedPrivacyPolicyVersion으로 회원 약관 얻기")
    @Test
    void getByOriginalMemberUuidTest() {
        // given
        SiteMemberTermEntity memberTermEntity = createMemberTermUserEntityWithUuid();
        SiteMemberEntity memberEntity = memberTermEntity.getMember();

        given(memberTermRepository.findByUuid(memberTermEntity.getUuid())).willReturn(Optional.of(memberTermEntity));
        given(memberTermRepository.save(memberTermEntity)).willReturn(memberTermEntity);
        given(memberTermRepository.findByAgreedPrivacyPolicyVersion(memberTermEntity.getAgreedPrivacyPolicyVersion())).willReturn(List.of(memberTermEntity));

        // when
        ConvCommentResponse commentResponse = commentApplicationService.insert(insertRequest);

        // then
        assertThat(commentApplicationService.getByAgreedPrivacyPolicyVersion(memberTermEntity.getAgreedPrivacyPolicyVersion()).getFirst()).isEqualTo(commentResponse);
    }

    @DisplayName("agreedAdInfoReceivingVersion으로 회원 약관 얻기")
    @Test
    void getByEmailTest() {
        // given
        SiteMemberTermEntity memberTermEntity = createMemberTermUserEntityWithUuid();
        SiteMemberEntity memberEntity = memberTermEntity.getMember();

        given(memberTermRepository.findByUuid(memberTermEntity.getUuid())).willReturn(Optional.of(memberTermEntity));
        given(memberTermRepository.save(memberTermEntity)).willReturn(memberTermEntity);
        given(memberTermRepository.findByAgreedAdInfoReceivingVersion(memberTermEntity.getAgreedAdInfoReceivingVersion())).willReturn(List.of(memberTermEntity));

        // when
        ConvCommentResponse commentResponse = commentApplicationService.insert(insertRequest);

        // then
        assertThat(commentApplicationService.getByAgreedAdInfoReceivingVersion(memberTermEntity.getAgreedAdInfoReceivingVersion()).getFirst()).isEqualTo(commentResponse);
    }

    @DisplayName("빈 회원 약관 얻기")
    @Test
    void getOptionalEmptyTest() {
        // given
        SiteMemberTermEntity memberTermEntity = createMemberTermUserEntityWithUuid();
        SiteMemberEntity memberEntity = memberTermEntity.getMember();
        UUID uuid = memberEntity.getUuid();

        // getByUuid
        // given & when
        given(memberTermRepository.findByUuid(uuid)).willReturn(Optional.empty());

        // then
        assertThat(commentApplicationService.getByUuid(uuid)).isEmpty();

        // getByMember
        // given & when
        given(memberTermRepository.findByMember(memberEntity)).willReturn(Optional.empty());

        // then
        assertThat(commentApplicationService.getByMember(memberEntity)).isEmpty();
    }

    @DisplayName("uuid로 회원 약관 제거")
    @Test
    void removeByUuidTest() {
        // given
        SiteMemberTermEntity memberTermEntity = createMemberTermUserEntityWithUuid();
        SiteMemberEntity memberEntity = memberTermEntity.getMember();
        UUID uuid = memberEntity.getUuid();

        given(memberRepository.findByUuid(uuid)).willReturn(Optional.of(memberEntity));
        given(memberRepository.save(createMemberBasicUserEntity())).willReturn(memberEntity);
        given(memberTermRepository.findByUuid(uuid)).willReturn(Optional.empty()).willReturn(Optional.of(memberTermEntity)).willReturn(Optional.empty());
        given(memberTermRepository.save(memberTermEntity)).willReturn(memberTermEntity);
        willDoNothing().given(memberTermRepository).deleteByUuid(uuid);

        // when
        commentApplicationService.insert(insertRequest);
        commentApplicationService.removeByUuid(uuid);

        // then
        assertThat(commentApplicationService.getByUuid(uuid)).isEmpty();
    }
}
