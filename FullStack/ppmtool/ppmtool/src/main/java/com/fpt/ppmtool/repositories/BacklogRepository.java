package com.fpt.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fpt.ppmtool.domain.Backlog;
@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long>  {
	Backlog findByProjectIdentifier(String Identifier);
}