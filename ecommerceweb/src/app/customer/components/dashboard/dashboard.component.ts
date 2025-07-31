import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

    products:any[]=[];
  
    constructor(private customerService:CustomerService,
        private fb: FormBuilder,
        private snackBar: MatSnackBar
    ){}
  
  
    ngOnInit(){
      this.getAllProducts();
    }
  
    getAllProducts(){
      this.products=[];
      this.customerService.getallProducts().subscribe(res=>{
        res.forEach(element => {
          element.processedImg= 'data:image/jpeg;base64,'+element.byteimg;
          this.products.push(element);
          
        });
      })
    }

    addToCart(id:any){
      //console.log(id)
      this.customerService.addToCart(id).subscribe(res=>{
        this.snackBar.open("Product added to cart successfully","Close",{duration : 5000})
      })

    }

}
