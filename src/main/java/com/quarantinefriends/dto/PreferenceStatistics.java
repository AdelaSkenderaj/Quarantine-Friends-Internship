package com.quarantinefriends.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PreferenceStatistics {

    private PreferenceDTO preference;
    private Long timesChosen;
}
