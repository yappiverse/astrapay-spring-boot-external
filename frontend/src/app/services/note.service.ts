import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note, NoteRequest } from '../models/note.model';

@Injectable({ providedIn: 'root' })
export class NoteService {
  private http = inject(HttpClient);
  private baseUrl = '/api/notes';

  getAll(): Observable<Note[]> {
    return this.http.get<Note[]>(this.baseUrl);
  }

  create(request: NoteRequest): Observable<Note> {
    return this.http.post<Note>(this.baseUrl, request);
  }

  deleteById(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
