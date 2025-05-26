package com.gft.user.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class FeatureFlags {
    @Value("${app.toggleNotifications}")
    private volatile boolean toggleNotifications;

    @Value("${app.communicationsBackendUrl}")
    private volatile String communicationsBackendUrl;
}
