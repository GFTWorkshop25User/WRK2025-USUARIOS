package com.gft.user.infrastructure.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.user.favorites.AddUserFavoriteProductUseCase;
import com.gft.user.application.user.favorites.GetUserFavoriteProductsUseCase;
import com.gft.user.application.user.favorites.GetUserIdsByFavoriteProductIdUseCase;
import com.gft.user.application.user.favorites.RemoveUserFavoriteProductUseCase;
import com.gft.user.domain.exception.ProductAlreadyInFavoritesException;
import com.gft.user.domain.exception.ProductNotInFavoritesException;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserFavoritesController.class)
public class UserFavoritesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GetUserFavoriteProductsUseCase getUserFavoriteProductsUseCase;

    @MockitoBean
    private AddUserFavoriteProductUseCase addUserFavoriteProductUseCase;

    @MockitoBean
    private RemoveUserFavoriteProductUseCase removeUserFavoriteProductUseCase;

    @MockitoBean
    private GetUserIdsByFavoriteProductIdUseCase getUserIdsByFavoriteProductIdUseCase;

    @Test
    void should_addProduct_when_addProductToFavoritesCalled() throws Exception {
        UUID uuid = UUID.randomUUID();

        doNothing().when(addUserFavoriteProductUseCase).execute(uuid, 4L);

        mockMvc.perform(put("/api/v1/users/{id}/favorite-products/add", uuid)
                        .content(objectMapper.writeValueAsString(4L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(addUserFavoriteProductUseCase, times(1)).execute(uuid, 4L);
    }

    @Test
    void should_responseNotFound_when_userNotFoundForGetFavoriteIds() throws Exception {
        UUID uuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(getUserFavoriteProductsUseCase).execute(uuid);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/favorite-products", uuid)).andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_returnOk_when_userFoundForGetFavoriteIds() throws Exception {
        UUID uuid = UUID.randomUUID();

        Set<Long> favoriteIds = new HashSet<>();
        favoriteIds.add(4L);
        when(getUserFavoriteProductsUseCase.execute(uuid)).thenReturn(favoriteIds);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/favorite-products", uuid)).andExpect(status().isOk()).andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(favoriteIds);
        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void should_noResponse_when_removeLoyaltyPoints() throws Exception {
        UUID uuid = UUID.randomUUID();
        long productId = 4L;

        doNothing().when(removeUserFavoriteProductUseCase).execute(uuid, productId);

        mockMvc.perform(put("/api/v1/users/{id}/favorite-products/remove", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(removeUserFavoriteProductUseCase, times(1)).execute(uuid, productId);
    }

    @Test
    void should_responseNotFound_when_userNotFoundForRemoveLoyaltyPoints() throws Exception {
        UUID uuid = UUID.randomUUID();
        long productId = 4L;

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(removeUserFavoriteProductUseCase).execute(uuid, productId);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/favorite-products/remove", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseNotFound_when_favoriteToRemoveNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();
        long productId = 8L;

        doThrow(new ProductNotInFavoritesException("Product 8 is not in favorite products")).when(removeUserFavoriteProductUseCase).execute(uuid, productId);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/favorite-products/remove", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        assertEquals("Product 8 is not in favorite products", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseBadRequest_when_productIsAlreadyInFavorites() throws Exception {
        UUID uuid = UUID.randomUUID();
        Long productId = 4L;

        doThrow(new ProductAlreadyInFavoritesException("Product is already in favorites"))
                .when(addUserFavoriteProductUseCase).execute(uuid, productId);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/favorite-products/add", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        assertEquals("Product is already in favorites", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_returnUserIds_when_productIdAmongThem() throws Exception {
        Long productId = 4L;
        List<UUID> userIds = List.of(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        );

        when(getUserIdsByFavoriteProductIdUseCase.execute(productId)).thenReturn(userIds);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/favorite-product/{productId}", productId)).andExpect(status().isOk()).andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(userIds);
        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
        verify(getUserIdsByFavoriteProductIdUseCase, times(1)).execute(productId);

    }

}
