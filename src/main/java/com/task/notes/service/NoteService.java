package com.task.notes.service;

import com.task.notes.dto.NoteDTO;

import java.util.List;

public interface NoteService {


    List<NoteDTO> getAllNotes();

    NoteDTO createNote(NoteDTO note);

    NoteDTO getNoteById(String id);

    NoteDTO likeNote(String noteId, String userName);

    NoteDTO unlikeNote(String noteId, String userName);
}
