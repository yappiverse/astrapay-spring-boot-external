package com.astrapay.controller;

import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteRequestDto;
import com.astrapay.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@Api(value = "NoteController")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    @ApiOperation(value = "Get all notes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NoteDto.class, responseContainer = "List")
    })
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        log.info("GET /api/notes");
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PostMapping
    @ApiOperation(value = "Create a new note")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = NoteDto.class),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteRequestDto request) {
        log.info("POST /api/notes - title: {}", request.getTitle());
        NoteDto created = noteService.createNote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a note by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        log.info("DELETE /api/notes/{}", id);
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @ApiOperation(value = "Delete a note by title")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity<Void> deleteNoteByTitle(@RequestParam String title) {
        log.info("DELETE /api/notes?title={}", title);
        noteService.deleteNoteByTitle(title);
        return ResponseEntity.noContent().build();
    }
}
