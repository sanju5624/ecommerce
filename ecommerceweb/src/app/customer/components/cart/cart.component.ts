import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  cartItems: any[]=[];

  order:any;

  constructor(
    private customerService: CustomerService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    private dialog: MatDialog
  ){}

  ngOnInit():void{
    this.getCart();
  }

  getCart(){
    this.cartItems=[];
    this.customerService.getCartByUserId().subscribe(res=>{
      this.order=res;
      res.cartItems.forEach(element =>{
        element.processedImg= 'data:image/jpeg;base64,' + element.returnedImg;
        this.cartItems.push(element);
      })
    })
  }

  increaseQuantity(productId : any){
    this.customerService.increaseProductQuantity(productId).subscribe(res =>{
      this.snackBar.open('Product quantity increased','Close',{duration : 5000});
      this.getCart();
    });

  }

   decreaseQuantity(productId : any){
    this.customerService.decreaseProductQuantity(productId).subscribe(res =>{
      this.snackBar.open('Product quantity decreased','Close',{duration : 5000});
      this.getCart();
    });

  }


}
