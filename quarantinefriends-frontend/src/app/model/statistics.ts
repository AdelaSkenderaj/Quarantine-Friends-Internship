import { Hobby, Preference } from "./model";

export class HobbyStatistics {
    public hobby:Hobby;
    public timesChosen:number;
    constructor() {}
}

export class PreferenceStatistics {
    public preference:Preference;
    public timesChosen:number;
    constructor() {}
}

export class Statistics {
    public hobbyStatistics: HobbyStatistics[];
    public preferenceStatistics: PreferenceStatistics[];
    public nrUsers:number;
    public nrExchangedMessages: number;
    public nrBannedUsers: number;
    public nrMatches:number;

    constructor() {}
}
