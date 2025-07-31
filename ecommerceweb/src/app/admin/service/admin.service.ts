import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../services/storage/user-storage.service';

const BASIC_URL="http://localhost:8080/"

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  addCategory(categoryDto:any):Observable<any>{
    return this.http.post(BASIC_URL+'api/admin/category',categoryDto,{
      headers:this.createAuthorizationHeader(),
    })
  }

  getallCategories():Observable<any>{
    return this.http.get(BASIC_URL+'api/admin',{
      headers:this.createAuthorizationHeader(),
    })
  }

  addproduct(productDto:any):Observable<any>{
    return this.http.post(BASIC_URL+'api/admin/product',productDto,{
      headers:this.createAuthorizationHeader(),
    })
  }

   getallProducts():Observable<any>{
    return this.http.get(BASIC_URL+'api/admin/products',{
      headers:this.createAuthorizationHeader(),
    })
  }

    deleteproduct(productId:any):Observable<any>{
    return this.http.delete(BASIC_URL+`api/admin/product/${productId}`,{
      headers:this.createAuthorizationHeader(),
    })
  }


  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer '+UserStorageService.getToken()
    )
  }

}
