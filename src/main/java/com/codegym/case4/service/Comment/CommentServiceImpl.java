package com.codegym.case4.service.Comment;

import com.codegym.case4.model.Comment;
import com.codegym.case4.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService{
    @Autowired
    ICommentRepository commentRepository;

    @Override
    public Page<Comment> findCommentByBookId(Long bookId, Pageable pageable) {
        return commentRepository.findCommentByBookId(bookId, pageable);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }
}
