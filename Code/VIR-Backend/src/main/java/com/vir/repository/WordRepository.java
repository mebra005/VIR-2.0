package com.vir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vir.model.Word;

@Repository
public interface  WordRepository extends PagingAndSortingRepository<Word, Long> {
		
	@Query("SELECT w FROM Word w WHERE w.value IN (:words)")
	List<Word> findAllIn(@Param("words") List<String> words);
	
	Word findFirstByValue(String value);
}