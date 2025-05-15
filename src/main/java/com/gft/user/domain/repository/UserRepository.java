package com.gft.user.domain.repository;

import com.gft.user.domain.model.user.User;

import java.util.UUID;

public interface UserRepository {
    UUID save(User user);
}
