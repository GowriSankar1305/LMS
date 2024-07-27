package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.lms.entity.BookEntity;
import java.util.List;


public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {
	
	BookEntity findByBookId(Long bookId);
	List<BookEntity> findByBookCategory_CategoryId(Long categoryId);
	@Query("from BookEntity be where be.bookId in :ids")
	List<BookEntity> findBooksByBookIds(@Param("ids") List<Long> bookIds);
}
