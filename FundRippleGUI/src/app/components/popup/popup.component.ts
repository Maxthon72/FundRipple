import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostUnderProjectWrite } from 'src/app/interfaces/Project/PostUnderProject';
import { User } from 'src/app/interfaces/User/fullUser';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.css']
})
export class PopupComponent {
  post:PostUnderProjectWrite = {
    post:"",
    url:""
  }
  reason:string=""
  operation:string=""
  user:User|null=null;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.operation=data.myParam
    if(this.operation=='MU'){
      this.user=data.user
    }
  }

}
