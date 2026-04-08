package com.astrapay.service;

import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteRequestDto;
import com.astrapay.exception.NoteNotFoundException;
import com.astrapay.exception.NoteTitleAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteServiceImplTest {

    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteServiceImpl();
    }

    @Test
    void getAllNotes_whenEmpty_returnsEmptyList() {
        List<NoteDto> result = noteService.getAllNotes();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllNotes_afterCreate_returnsOneNote() {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul");
        request.setContent("Isi");

        noteService.createNote(request);
        List<NoteDto> result = noteService.getAllNotes();

        assertEquals(1, result.size());
    }

    @Test
    void createNote_validRequest_returnsNoteDto() {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul Test");
        request.setContent("Isi catatan test");

        NoteDto result = noteService.createNote(request);

        assertNotNull(result.getId());
        assertEquals("Judul Test", result.getTitle());
        assertEquals("Isi catatan test", result.getContent());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void createNote_multipleNotes_haveUniqueIds() {
        NoteRequestDto req1 = new NoteRequestDto();
        req1.setTitle("Note 1");
        req1.setContent("Isi 1");

        NoteRequestDto req2 = new NoteRequestDto();
        req2.setTitle("Note 2");
        req2.setContent("Isi 2");

        NoteDto note1 = noteService.createNote(req1);
        NoteDto note2 = noteService.createNote(req2);

        assertNotEquals(note1.getId(), note2.getId());
    }

    @Test
    void deleteNote_existingId_removesNote() {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul");
        request.setContent("Isi");

        NoteDto created = noteService.createNote(request);
        noteService.deleteNote(created.getId());

        assertTrue(noteService.getAllNotes().isEmpty());
    }

    @Test
    void deleteNote_nonExistingId_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNote("non-existing-id"));
    }

    @Test
    void createNote_duplicateTitle_throwsNoteTitleAlreadyExistsException() {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul Sama");
        request.setContent("Isi pertama");
        noteService.createNote(request);

        NoteRequestDto duplicate = new NoteRequestDto();
        duplicate.setTitle("Judul Sama");
        duplicate.setContent("Isi kedua");

        assertThrows(NoteTitleAlreadyExistsException.class, () -> noteService.createNote(duplicate));
    }

    @Test
    void deleteNoteByTitle_existingTitle_removesNote() {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul Hapus");
        request.setContent("Isi");
        noteService.createNote(request);

        noteService.deleteNoteByTitle("Judul Hapus");

        assertTrue(noteService.getAllNotes().isEmpty());
    }

    @Test
    void deleteNoteByTitle_nonExistingTitle_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNoteByTitle("Tidak Ada"));
    }
}
