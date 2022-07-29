import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Message, Report, User } from 'src/app/model/model';
import { MatchService } from 'src/app/services/match.service';
import { MessageService } from 'src/app/services/message.service';
import { ReportService } from 'src/app/services/report.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  user:User;
  friends: User[] = [];
  chattingFriend:User;
  areFriends:boolean;
  messages: Message[] = [];
  @ViewChild('input') input: ElementRef;
  @ViewChild('scrollMe') private myScrollContainer: ElementRef;

  constructor(
    private userService:UserService,
    private activatedRoute:ActivatedRoute,
    private messageService:MessageService,
    private reportService:ReportService,
    private router:Router,
    private matchService:MatchService,

    ) { }

  ngOnInit(): void {
    this.refreshData();
  }

  refreshData() {
    this.userService.userLoggedIn.subscribe((response)=> {
      this.user = response;
      this.userService.getFriendsByUserId(this.user.id).subscribe((response) => {
        this.friends = response;
        this.activatedRoute.paramMap.subscribe((paramMap)  => {
          let friendId = +paramMap.get('id');
          this.userService.getUserById(friendId).subscribe((response) => {
            this.chattingFriend = response;
            this.userService.checkIfFriends(this.user.id, this.chattingFriend.id).subscribe((response:boolean) => {
              this.areFriends = response;
              if(this.areFriends) {
                this.messageService.getMessagesForFriends(this.user.id, this.chattingFriend.id).subscribe((response) => {
              this.messages = response;
              this.scrollToBottom();
            });
              }
              else{
                this.router.navigate(['/friends']);
              }
            })
            
          });
        });
      });
    });
  }

  scrollToBottom() : void {
    try{
      this.myScrollContainer.nativeElement.scrollToBottom = this.myScrollContainer.nativeElement.scrollHeight;
      console.log(this.myScrollContainer.nativeElement.scrollHeight);
      console.log(this.myScrollContainer.nativeElement.scrollToBottom );
    } catch(err){
      console.log(err);
    }
  }

  onClick(){
    let messageString = this.input.nativeElement.value;
    let message = new Message();
    message.message = messageString;
    message.fromUser = this.user;
    message.toUser = this.chattingFriend;
    this.messageService.addMessage(message).subscribe((response)=> {
      this.refreshData();
    })
    this.input.nativeElement.value='';
  }

  blockUser() {
    this.userService.blockUser(this.chattingFriend.id, this.user).subscribe((response) => {
      this.refreshData();
      this.router.navigate(['/friends']);
    })

  }

  reportUser() {
    let report = new Report();
    report.user = this.chattingFriend;
    report.date = new Date();
    this.reportService.reportUser(report).subscribe();
  }

  unMatch(id:number) {
    this.matchService.unmatch(id, this.user).subscribe((response) => {
      this.refreshData();
    })
  }

}
