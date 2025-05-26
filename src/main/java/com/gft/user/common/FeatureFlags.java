package com.gft.user.common;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Generated
public class FeatureFlags {
    @Value("${app.toggleNotifications}")
    private volatile boolean toggleNotifications;
}
