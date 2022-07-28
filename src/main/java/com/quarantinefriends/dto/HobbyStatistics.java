package com.quarantinefriends.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class HobbyStatistics {

    private HobbyDTO hobby;
    private Long timesChosen;
}
