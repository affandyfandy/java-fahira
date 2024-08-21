import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { User } from '../models/user.model';

const baseUrl = 'http://localhost:3000/user';

@Injectable({
  providedIn: 'root',
})
export class UserService{

  constructor(private http: HttpClient) {}

  authenticate(username: string, password: string): Observable<User | null> {
    return this.http.get<User[]>(`${baseUrl}?username=${username}&password=${password}`).pipe(
      map(users => users.length > 0 ? users[0] : null),
    );
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(baseUrl);
  }

  get(id: any): Observable<User> {
    return this.http.get<User>(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }
}
