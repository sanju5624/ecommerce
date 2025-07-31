import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../services/storage/user-storage.service';

const BASIC_URL="http://localhost:8080/"
@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

    getallProducts():Observable<any>{
      // console.log(categoryDto);
      return this.http.get(BASIC_URL+'api/customer/products',{
        headers:this.createAuthorizationHeader(),
      })
    }

    addToCart(productId:any):Observable<any>{

      const cartDto={
        productId : productId,
        userId: UserStorageService.getUserId()

      }
      return this.http.post(BASIC_URL+'api/customer/cart',cartDto,{
        headers:this.createAuthorizationHeader(),
      })
    }

    increaseProductQuantity(productId:any):Observable<any>{

      const cartDto={
        productId : productId,
        userId: UserStorageService.getUserId()

      }
      return this.http.post(BASIC_URL+'api/customer/addition',cartDto,{
        headers:this.createAuthorizationHeader(),
      })
    }

    decreaseProductQuantity(productId:any):Observable<any>{

      const cartDto={
        productId : productId,
        userId: UserStorageService.getUserId()

      }
      return this.http.post(BASIC_URL+'api/customer/deduction',cartDto,{
        headers:this.createAuthorizationHeader(),
      })
    }

     getCartByUserId():Observable<any>{

      const userId = UserStorageService.getUserId();
      return this.http.get(BASIC_URL+`api/customer/cart/${userId}`,{
        headers:this.createAuthorizationHeader(),
      })
    }

      private createAuthorizationHeader():HttpHeaders{
        return new HttpHeaders().set(
          'Authorization','Bearer '+UserStorageService.getToken()
        )
      }
}
