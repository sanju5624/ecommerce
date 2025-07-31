import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  products:any[]=[];

  constructor(private adminService:AdminService,
      private fb: FormBuilder,
      private snackBar: MatSnackBar
  ){}


  ngOnInit(){
    this.getAllProducts();
  }

  getAllProducts(){
    this.products=[];
    this.adminService.getallProducts().subscribe(res=>{
      res.forEach(element => {
        element.processedImg= 'data:image/jpeg;base64,'+element.byteimg;
        this.products.push(element);
        
      });
    })
  }

  deleteProduct(productId:any){
    console.log(productId);
    this.adminService.deleteproduct(productId).subscribe(res=>{
      if(res.body==null){
        this.snackBar.open('Product Deleted Successfully','Close',{
          duration:5000
        });
        this.getAllProducts();
      }
      else{
         this.snackBar.open(res.message,'Close',{
          duration:5000,
          panelClass:'error-snackbar'
        });
      }
    })
  }

}
