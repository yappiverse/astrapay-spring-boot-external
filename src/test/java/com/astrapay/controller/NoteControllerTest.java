package com.astrapay.controller;

import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteRequestDto;
import com.astrapay.exception.NoteNotFoundException;
import com.astrapay.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@SuppressWarnings("null")
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @Test
    void getAllNotes_returnsEmptyList() throws Exception {
        when(noteService.getAllNotes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllNotes_returnsListOfNotes() throws Exception {
        NoteDto note = NoteDto.builder()
                .id("uuid-1")
                .title("Judul")
                .content("Isi")
                .createdAt(LocalDateTime.now())
                .build();
        when(noteService.getAllNotes()).thenReturn(List.of(note));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("uuid-1"))
                .andExpect(jsonPath("$[0].title").value("Judul"))
                .andExpect(jsonPath("$[0].content").value("Isi"));
    }

    @Test
    void createNote_validRequest_returns201() throws Exception {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul Baru");
        request.setContent("Isi Catatan");

        NoteDto response = NoteDto.builder()
                .id("uuid-1")
                .title("Judul Baru")
                .content("Isi Catatan")
                .createdAt(LocalDateTime.now())
                .build();
        when(noteService.createNote(any(NoteRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("uuid-1"))
                .andExpect(jsonPath("$.title").value("Judul Baru"));
    }

    @Test
    void createNote_emptyTitle_returns400() throws Exception {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("");
        request.setContent("Isi Catatan");

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void createNote_emptyContent_returns400() throws Exception {
        NoteRequestDto request = new NoteRequestDto();
        request.setTitle("Judul");
        request.setContent("");

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    void createNote_nullTitle_returns400() throws Exception {
        String body = "{\"title\": null, \"content\": \"Isi\"}";

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void deleteNote_existingId_returns204() throws Exception {
        doNothing().when(noteService).deleteNote("uuid-1");

        mockMvc.perform(delete("/api/notes/uuid-1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNote_nonExistingId_returns404() throws Exception {
        doThrow(new NoteNotFoundException("non-existing-id")).when(noteService).deleteNote("non-existing-id");

        mockMvc.perform(delete("/api/notes/non-existing-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
}
