package com.boot.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.lms.entity.BookCategoryEntiy;

public interface BookCategoryEntiyRepository extends JpaRepository<BookCategoryEntiy, Long> {
	BookCategoryEntiy findByCategoryName(String categoryName);
}
