import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  loginForm! : FormGroup;

  hidePassword = true;

   constructor(private fb : FormBuilder,
      private snackBar: MatSnackBar,
      private authService: AuthService,
      private router : Router
     ){}

     ngOnInit():void{
      this.loginForm=this.fb.group({
        email : [null , Validators.required],
        password : [null , Validators.required],

      })
     }

     togglePasswordVisibility(){
    this.hidePassword=!this.hidePassword;
   }

   onSubmit():void{
   
    const username=this.loginForm.get('email')!.value;
    const password=this.loginForm.get('password')!.value;

    this.authService.login(username,password).subscribe(
      (res)=>{
        console.log("afklahwglk")
        if(UserStorageService.isAdminLoggedIn()){
          console.log("ajfa;")
          this.router.navigateByUrl('admin/dashboard');
        }
        else if(UserStorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl('customer/dashboard');
        }
        console.log("jasflj")
      },
      (error)=>{
            this.snackBar.open('Bad Credentials ','ERROR',{ duration: 5000});

      }
    )
   }

}
