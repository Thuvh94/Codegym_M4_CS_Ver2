package com.codegym.case4.service.Comment;

import com.codegym.case4.model.Comment;
import com.codegym.case4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ICommentService extends IGeneralService<Comment> {
    Page<Comment> findCommentByBookId(Long bookId, Pageable pageable);
}
