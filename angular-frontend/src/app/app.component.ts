import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { debounceTime, distinctUntilChanged, filter, map, Observable, startWith, switchMap } from 'rxjs';
import { SearchResult } from './search-result';
import { SearchService } from './search.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit {

  searchForm = new FormGroup({
    q: new FormControl('')
  })

  result$: Observable<SearchResult[]>
  sseResult$: Observable<SearchResult[]>

  constructor(private searchService: SearchService) { }


  ngOnInit(): void {
    let input$ = this.searchForm.get('q')?.valueChanges.pipe(
      map(i => i.trim()),
      filter(i => i !== ""),
      debounceTime(300),
      distinctUntilChanged()
    )

    this.result$ = input$.pipe(
      switchMap(input => this.searchService.search(input).pipe(
        startWith([])
      ))
    )

    this.sseResult$ = input$.pipe(
      switchMap(input => this.searchService.sse(input).pipe(
        startWith([])
      ))
    )
  }

}
