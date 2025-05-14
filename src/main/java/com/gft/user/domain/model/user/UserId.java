package com.gft.user.domain.model.user;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public class UserId {

    UUID uuid;

    UserId() {
        uuid = UUID.randomUUID();
    }

}