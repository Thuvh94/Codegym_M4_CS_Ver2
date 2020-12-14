package com.codegym.case4.repository;

import com.codegym.case4.model.Comment;
import com.codegym.case4.model.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select * from comments comment where comment.book =:id",nativeQuery = true)
    Page<Comment> findCommentByBookId(@Param("id") Long bookId, Pageable pageable);
}
