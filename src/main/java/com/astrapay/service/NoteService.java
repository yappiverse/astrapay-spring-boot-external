package com.astrapay.service;

import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteRequestDto;

import java.util.List;

public interface NoteService {

    List<NoteDto> getAllNotes();

    NoteDto createNote(NoteRequestDto request);

    void deleteNote(String id);
}
