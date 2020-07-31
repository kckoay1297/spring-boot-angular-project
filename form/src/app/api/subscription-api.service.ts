import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionApiService {

  public API = '//localhost:5007/subscription';
  public CREATION_API = this.API + '/create';
  public GET_API = this.API + '/get';

  constructor(private http: HttpClient) { }

  get(id:string) {
    return this.http.get(this.GET_API + '/' + id);
  }

  save(data: any): Observable<any> {
    let result: Observable<any>;
    if (data.href) {
      result = this.http.put(data.href, data);
    } else {
      result = this.http.post(this.CREATION_API, data);
    }
    return result;
  }
}
