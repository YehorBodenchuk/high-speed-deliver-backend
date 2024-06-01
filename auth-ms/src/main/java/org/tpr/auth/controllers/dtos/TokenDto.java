package org.tpr.auth.controllers.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

    private String accessToken;

    private String refreshToken;

    private long accessTokenExpire;

    private long refreshTokenExpire;
}
