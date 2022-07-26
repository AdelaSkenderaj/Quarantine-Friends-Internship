
export class User {
    public id: number;
    public role: Role;
    public photo: string;
    public accountTerminated: boolean;
    public firstName: string;
    public lastName: string;
    public email: string;
    public username: string;
    public password: string;
    public hobbies: Hobby[];
    public age: number;
    public preferences: Preference[];
    
}

export class Role {
    public id: number;
    public name: string;
}

export class Report {
    public id:number;
    public user:User;
    public date: Date;
    constructor() {}
}

export class Preference {
    public id: number;
    public name: string;
}

export class Message {
    public id:number;
    constructor(
        public  message: string,
        public fromUser: User,
        public toUser: User
    ) {}
}
    
export class MatchRequest {
    public id:number;
    public fromUser: User;
    public toUser:User;

    constructor() {}
}

export class Hobby {
    public id:number;
    public name:string;
}

export class MatchSuggestion {
    public user:User;
    public matchingPercentage:number;
    constructor() {}
}