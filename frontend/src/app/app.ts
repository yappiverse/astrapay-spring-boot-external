import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { NoteService } from './services/note.service';
import { Note } from './models/note.model';

@Component({
  selector: 'app-root',
  imports: [FormsModule, DatePipe],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  private noteService = inject(NoteService);

  notes = signal<Note[]>([]);
  loading = signal(false);
  submitting = signal(false);
  error = signal<string | null>(null);
  formError = signal<string | null>(null);
  deletingId = signal<string | null>(null);

  title = '';
  content = '';

  ngOnInit() {
    this.loadNotes();
  }

  loadNotes() {
    this.loading.set(true);
    this.error.set(null);
    this.noteService.getAll().subscribe({
      next: (notes) => {
        this.notes.set(notes);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Gagal memuat notes. Pastikan backend sudah berjalan.');
        this.loading.set(false);
      },
    });
  }

  createNote() {
    if (!this.title.trim() || !this.content.trim()) {
      this.formError.set('Judul dan isi tidak boleh kosong.');
      return;
    }
    this.formError.set(null);
    this.submitting.set(true);
    this.noteService.create({ title: this.title.trim(), content: this.content.trim() }).subscribe({
      next: (note) => {
        this.notes.update((n) => [note, ...n]);
        this.title = '';
        this.content = '';
        this.submitting.set(false);
      },
      error: (err) => {
        this.formError.set(this.getErrorMessage(err, 'Gagal membuat note.'));
        this.submitting.set(false);
      },
    });
  }

  deleteNote(id: string) {
    this.deletingId.set(id);
    this.noteService.deleteById(id).subscribe({
      next: () => {
        this.notes.update((n) => n.filter((note) => note.id !== id));
        this.deletingId.set(null);
      },
      error: () => {
        this.error.set('Gagal menghapus note.');
        this.deletingId.set(null);
      },
    });
  }

  dismissError() {
    this.error.set(null);
  }

  private getErrorMessage(error: unknown, fallback: string): string {
    if (error instanceof HttpErrorResponse) {
      const response = error.error;

      if (typeof response === 'string' && response.trim()) {
        return response;
      }

      if (response && typeof response === 'object') {
        const entries = Object.entries(response as Record<string, unknown>)
          .filter(([, value]) => typeof value === 'string' && value.trim().length > 0)
          .map(([, value]) => String(value));

        if (entries.length > 0) {
          return entries.join(' ');
        }
      }

      if (error.message) {
        return error.message;
      }
    }

    return fallback;
  }
}

