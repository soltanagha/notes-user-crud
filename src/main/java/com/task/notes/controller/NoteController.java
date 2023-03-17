package com.task.notes.controller;

import com.task.notes.dto.NoteDTO;
import com.task.notes.entity.Response;
import com.task.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<Response> findAllNotes() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("notes",noteService.getAllNotes()))
                        .message("Fetched all notes!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody NoteDTO note) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.createNote(note)))
                        .message("Saved new Note!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/like/{noteId}")
    public ResponseEntity<Response> likeNote(@PathVariable String noteId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.likeNote(noteId, userDetails.getUsername())))
                        .message("Note liked!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/unlike/{noteId}")
    public ResponseEntity<Response> unlikeNote(@PathVariable String noteId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("note",noteService.unlikeNote(noteId, userDetails.getUsername())))
                        .message("Note unliked!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
