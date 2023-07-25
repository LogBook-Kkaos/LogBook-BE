package com.logbook.backend.logbookbe.global.oauth;

import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import com.logbook.backend.logbookbe.domain.user.type.Vendor;
import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;
import com.logbook.backend.logbookbe.global.oauth.attribute.GoogleOAuthAttribute;
import com.logbook.backend.logbookbe.global.oauth.attribute.KakaoOAuthAttribute;
import com.logbook.backend.logbookbe.global.oauth.attribute.NaverOAuthAttribute;
import com.logbook.backend.logbookbe.global.oauth.attribute.OAuthAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthDetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String vendor = userRequest.getClientRegistration().getRegistrationId();
        Optional<OAuthAttribute> attribute = Optional.empty();

        if (vendor.equalsIgnoreCase(Vendor.NAVER.getVendor())) {
            attribute = Optional.of(new NaverOAuthAttribute((Map) oAuth2User.getAttributes().get("response")));
        }
        if (vendor.equalsIgnoreCase(Vendor.GOOGLE.getVendor())) {
            attribute = Optional.of(new GoogleOAuthAttribute(oAuth2User.getAttributes()));
        }
        if (vendor.equalsIgnoreCase(Vendor.KAKAO.getVendor())) {
            attribute = Optional.of(new KakaoOAuthAttribute((Map) oAuth2User.getAttributes().get("kakao_account")));
        }

        OAuthAttribute attr = attribute.orElseThrow(() -> new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR));
        String email = attr.getEmail();
        String name = attr.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            User createdUser = User.builder()
                    .email(email)
                    .userName(name)
                    .vendor(Vendor.valueOf(vendor.toUpperCase()))
                    .build();
            userRepository.save(createdUser);
        }

        return new OAuthDetails(email, name, attr.getAttributes());
    }
}
