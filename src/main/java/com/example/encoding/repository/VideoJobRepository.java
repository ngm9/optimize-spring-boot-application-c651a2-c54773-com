package com.example.encoding.repository;

import com.example.encoding.model.VideoJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoJobRepository extends JpaRepository<VideoJob, Long> {
}
