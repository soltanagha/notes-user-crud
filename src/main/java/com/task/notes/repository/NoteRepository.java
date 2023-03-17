package com.task.notes.repository;

import com.task.notes.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note,String> {
    List<Note> findAllByOrderByCreatedOnDesc();
}
