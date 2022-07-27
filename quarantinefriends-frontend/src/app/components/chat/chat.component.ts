import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Message, Report, User } from 'src/app/model/model';
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
  messages: Message[] = [];
  @ViewChild('input') input: ElementRef;
  @ViewChild('scrollMe') private myScrollContainer: ElementRef;

  constructor(    
    private userService:UserService,
    private activatedRoute:ActivatedRoute,
    private messageService:MessageService,
    private reportService:ReportService,
    private router:Router,

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
            this.messageService.getMessagesForFriends(this.user.id, this.chattingFriend.id).subscribe((response) => {
              this.messages = response;
              this.scrollToBottom();
            });
          });
        });
      });
    });
  }

  // refreshFriends() {
  //   this.userService.getFriendsByUserId(this.user.id).subscribe((response) => {
  //     this.friends = response;
  //   })
  // }

  // refreshData() {
  //   this.activatedRoute.paramMap.subscribe((paramMap)  => {
  //     let friendId = +paramMap.get('id');
  //     this.userService.getUserById(friendId).subscribe((response) => {
  //       this.chattingFriend = response;
  //       this.messageService.getMessagesForFriends(this.user.id, this.chattingFriend.id).subscribe((response) => {
  //         this.messages = response;
  //         this.scrollToBottom();
  //       })
  //     })
  //   })
  // }

  scrollToBottom() : void {
    try{
      this.myScrollContainer.nativeElement.scrollToBottom = this.myScrollContainer.nativeElement.scrollHeight;
    } catch(err){}
  }

  onClick(){
    let messageString = this.input.nativeElement.value;
    let message = new Message();
    message.message = messageString;
    message.fromUser = this.user;
    message.toUser = this.chattingFriend;
    this.messageService.addMessage(message).subscribe((response)=> {
      this.refreshData();
      this.scrollToBottom();
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
    report.user = this.user;
    report.date = new Date();
    this.reportService.reportUser(report).subscribe();
  }

}
