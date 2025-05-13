package kr.modusplant.modules.auth.normal.signup.mapper.domain;

import kr.modusplant.domains.member.app.http.request.SiteMemberTermInsertRequest;
import kr.modusplant.domains.member.app.http.response.SiteMemberResponse;
import kr.modusplant.modules.auth.normal.signup.app.http.request.NormalSignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMemberTermDomainMapper {

    @Mapping(target = "uuid", source = "memberResponse.uuid")
    SiteMemberTermInsertRequest toSiteMemberTermInsertRequest(NormalSignUpRequest request, SiteMemberResponse memberResponse);
}
