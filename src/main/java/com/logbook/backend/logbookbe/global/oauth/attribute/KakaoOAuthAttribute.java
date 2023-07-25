package com.logbook.backend.logbookbe.global.oauth.attribute;

import java.util.Map;

public class KakaoOAuthAttribute extends OAuthAttribute {
    public KakaoOAuthAttribute(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        Map<String, Object> subAttr = (Map<String, Object>) attributes.get("profile");
        return subAttr.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }
}
