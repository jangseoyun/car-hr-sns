package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.user.UserManagementUseCase;
import com.car.sns.application.usecase.user.UserReadUseCase;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.presentation.model.request.RegisterAccountRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[USER] - 사용자 controller 테스트")
@WebMvcTest(UserController.class)
class UserControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private UserManagementUseCase userManagementUseCase;
    @MockBean
    private UserReadUseCase userReadUseCase;

    private UserControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[SAVE] - 회원가입 성공")
    @Test
    void givenUserRegisterInfo_whenSavingUserAccount_thenReturnSuccessAccountResponse() throws Exception {
        //given
        RegisterAccountRequest registerAccountRequest = createRegisterAccountRequest();
        given(userManagementUseCase.userRegisterAccount(registerAccountRequest)).willReturn(createUserAccountDto());

        //when
        mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(registerAccountRequest))
        ).andExpect(status().isOk());
        //then
    }

    @DisplayName("[SAVE] - 회원가입 실패")
    @Test
    void givenUserAlreadyExistInfo_whenSavingUserAccount_thenReturnErrorAccountResponse() throws Exception {
        //given
        RegisterAccountRequest registerAccountRequest = createRegisterAccountRequest();

        //when
        mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict());

        //then

    }

    private RegisterAccountRequest createRegisterAccountRequest() {
        return RegisterAccountRequest.of(
                "userId",
                "password1234",
                "email@email.com",
                "nickname",
                "memo");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "userId",
                "password1234",
                "email@email.com",
                "nickname",
                "memo");
    }
}