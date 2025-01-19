import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class ApiReviewService {
	private host		= environment.apiHost
	private baseURL = `${this.host}/ReviewAPI`
	private apiURL	= `${this.baseURL}/api`

  constructor(private http: HttpClient) { }

  get<T>(url: string, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params: params, headers: headers, withCredentials: true };
    return this.http.get<T>(`${this.apiURL}/${url}`, options);
  }

  post<T>(url: string, body: any, headers?: HttpHeaders): Observable<T> {
    const options = { headers: headers, withCredentials: true };
    return this.http.post<T>(`${this.apiURL}/${url}`, body, options);
  }

	put<T>(url: string, body: any, headers?: HttpHeaders): Observable<T> {
    const options = { headers: headers, withCredentials: true };
    return this.http.put<T>(`${this.apiURL}/${url}`, body, options);
  }

  delete<T>(url: string, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params: params, headers: headers, withCredentials: true };
    return this.http.delete<T>(`${this.apiURL}/${url}`, options);
  }

	staticURL(url: string): string {
		return `${this.baseURL}${url}`
	}
}
