package kr.modusplant.modules.auth.normal.signup.mapper.domain;

import kr.modusplant.domains.member.app.http.request.SiteMemberAuthInsertRequest;
import kr.modusplant.domains.member.app.http.response.SiteMemberResponse;
import kr.modusplant.domains.member.enums.AuthProvider;
import kr.modusplant.modules.auth.normal.signup.app.http.request.NormalSignUpRequest;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMemberAuthDomainMapper {

    default SiteMemberAuthInsertRequest toSiteMemberAuthInsertRequest(NormalSignUpRequest request, SiteMemberResponse memberResponse) {
        return new SiteMemberAuthInsertRequest(memberResponse.uuid(), request.email(), request.pw(), AuthProvider.BASIC, null);
    }
}
