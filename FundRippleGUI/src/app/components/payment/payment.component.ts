import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/interfaces/Project/Project';
import { Payment } from 'src/app/interfaces/payment';
import { PaypalService } from 'src/app/services/paypal.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit{
  constructor(private route: ActivatedRoute,private payPalService:PaypalService,private router:Router){}
  status:string=""
  projectName:string=""
  amount:number=0
  ngOnInit(): void {
    this.route.params.subscribe(params => {
        this.status = params['status']
        this.projectName=params['projectName']
        this.amount = params['amount']
        let payment:Payment={
          money:this.amount,
          userName:"",
          projectName:this.projectName
        }
        if(this.status=="success"){
          this.payPalService.addPaymentToProject(payment).subscribe(
            (resp:Project)=>{
              this.router.navigate(['/project', this.projectName]);
            }
          )
        }
    })
  }

}
