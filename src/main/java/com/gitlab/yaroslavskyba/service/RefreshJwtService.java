package com.gitlab.yaroslavskyba.service;

import com.gitlab.yaroslavskyba.model.RefreshJwt;

public interface RefreshJwtService {
    String getRefreshJwtByValue(String value);
    RefreshJwt verifyExpiration(RefreshJwt refreshJwt);
}
