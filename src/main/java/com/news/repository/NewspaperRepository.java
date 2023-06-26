package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.news.entity.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {

}
