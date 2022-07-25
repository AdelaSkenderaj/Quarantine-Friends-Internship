package com.quarantinefriends.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MatchDTO {

    private UserDTO user;
    private double matchingPercentage;
}
