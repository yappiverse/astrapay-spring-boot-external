package com.astrapay.service;

import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteRequestDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.NoteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final List<Note> notes = new ArrayList<>();

    @Override
    public List<NoteDto> getAllNotes() {
        log.info("Fetching all notes, total: {}", notes.size());
        return notes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto createNote(NoteRequestDto request) {
        Note note = Note.builder()
                .id(UUID.randomUUID().toString())
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        notes.add(note);
        log.info("Created note with id: {}", note.getId());
        return toDto(note);
    }

    @Override
    public void deleteNote(String id) {
        Note note = notes.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoteNotFoundException(id));
        notes.remove(note);
        log.info("Deleted note with id: {}", id);
    }

    private NoteDto toDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .build();
    }
}
