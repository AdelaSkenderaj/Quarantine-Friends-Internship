package com.quarantinefriends.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class StatisticsDTO {

    private List<HobbyStatistics> hobbyStatistics;
    private List<PreferenceStatistics> preferenceStatistics;
    private Long nrUsers;
    private Long nrExchangedMessages;
    private Long nrBannedUsers;
    private Long nrMatches;


}
