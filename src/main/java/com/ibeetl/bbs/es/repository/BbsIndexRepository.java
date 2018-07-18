package com.ibeetl.bbs.es.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.ibeetl.bbs.es.entity.BbsIndex;

public interface BbsIndexRepository extends CrudRepository<BbsIndex, String>{

	List<BbsIndex> getByContent(String content);
	
	Page<BbsIndex> getByContent(String content,Pageable pageable);
	
}
