package com.gft.user.domain.model.user;

import lombok.Generated;
import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
@Getter
@Generated
public class UserId {

    UUID uuid;

    public UserId() {
        uuid = UUID.randomUUID();
    }

    private UserId(UUID uuid) {
        this.uuid = uuid;
    }

    public static UserId create(UUID uuid) {
        return new UserId(uuid);
    }

}