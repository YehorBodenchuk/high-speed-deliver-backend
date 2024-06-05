package org.tpr.auth.util;

import org.tpr.auth.controller.dto.user.UserLoginDto;
import org.tpr.auth.controller.dto.user.UserRegisterDto;
import org.tpr.auth.controller.dto.user.UserUpdateDto;
import org.tpr.auth.model.User;
import org.tpr.auth.model.enums.UserRole;

import java.util.Date;
import java.util.List;

public final class DataUtil {

    public static User getUserPersisted() {
        return User.builder()
                .id("1")
                .email("john.doe@gmail.com")
                .password("123")
                .roles(List.of(UserRole.USER))
                .phone("+380994545454")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    public static User getUserUpdated() {
        return User.builder()
                .id("1")
                .email("marry.sui@gmail.com")
                .password("456")
                .roles(List.of(UserRole.USER))
                .phone("+380993333333")
                .firstName("Marry")
                .lastName("Sui")
                .build();
    }

    public static User getUserNotPersisted() {
        return User.builder()
                .email("john.doe@gmail.com")
                .roles(List.of(UserRole.USER))
                .phone("+380994545454")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    public static UserRegisterDto getUserNotRegistered() {
        return UserRegisterDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .phone("+380994545454")
                .password("123")
                .confirmPassword("123")
                .build();
    }

    public static UserUpdateDto getUserNotUpdated() {
        return UserUpdateDto.builder()
                .email("marry.sui@gmail.com")
                .firstName("Marry")
                .lastName("Sui")
                .phone("+380993333333")
                .password("456")
                .build();
    }

    public static UserLoginDto getUserNotAuthenticated() {
        return UserLoginDto.builder()
                .email("john.doe@gmail.com")
                .password("123")
                .build();
    }

    public static User getUserDeleted() {
        return User.builder()
                .id("1")
                .email("john.doe@gmail.com")
                .password("123")
                .roles(List.of(UserRole.USER))
                .phone("+380994545454")
                .firstName("John")
                .lastName("Doe")
                .archiveDate(new Date())
                .build();
    }
}
