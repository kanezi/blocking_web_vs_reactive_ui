import { HttpClient } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { SearchResult } from './search-result';

@Injectable({
  providedIn: 'root'
})
export class SearchService {


  constructor(private http: HttpClient, private zone: NgZone) { }

  search(query: string): Observable<SearchResult[]> {
    return this.http.get<SearchResult[]>(`http://localhost:8080/api/search?q=${query}`);
  }

  sse(query: string): Observable<SearchResult[]> {
    let eventSource = new EventSource(`http://localhost:8080/api/search?q=${query}`);
    let results: SearchResult[] = []
    return new Observable(observer => {
      eventSource.onmessage = event => {
        results.push(JSON.parse(event.data))
        this.zone.run(() => observer.next(results))
      }

      eventSource.onerror = error => {
        if (eventSource.readyState === 0) {//remote server closed the connection
          eventSource.close()
          this.zone.run(() => observer.complete())
        } else {
          this.zone.run(() => observer.error(error))
        }
      }
    })
  }
}
