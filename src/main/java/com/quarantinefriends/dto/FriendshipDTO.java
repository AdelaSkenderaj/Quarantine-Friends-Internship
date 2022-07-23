package com.quarantinefriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FriendshipDTO {

    private Long id;
    private UserDTO friendOne;
    private UserDTO friendTwo;
}
