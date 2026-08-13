package com.example.encoding.service;

import com.example.encoding.model.VideoJob;
import com.example.encoding.repository.VideoJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class VideoJobService {
    private final VideoJobRepository repository;
    private ExecutorService executor;
    @Value("${thread.pool.size:8}")
    private int poolSize;
    public VideoJobService(VideoJobRepository repository) {
        this.repository = repository;
    }
    @PostConstruct
    public void init() {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }
    public CompletableFuture<VideoJob> submitJob(VideoJob job) {
        job.setStatus("QUEUED");
        job.setCreatedAt(java.time.Instant.now());
        job.setUpdatedAt(java.time.Instant.now());
        VideoJob saved = repository.save(job);
        CompletableFuture<VideoJob> future = CompletableFuture.supplyAsync(() -> {
            updateStatus(saved.getId(), "PROCESSING");
            // Simulate video processing workload
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            updateStatus(saved.getId(), "DONE");
            return repository.findById(saved.getId()).get();
        }, executor);
        return future;
    }
    public Optional<VideoJob> getJob(Long id) {
        return repository.findById(id);
    }
    public List<VideoJob> allJobs() {
        return repository.findAll();
    }
    public Map<String, Long> statusStats() {
        return repository.findAll().stream().collect(Collectors.groupingBy(VideoJob::getStatus, Collectors.counting()));
    }
    private void updateStatus(Long jobId, String status) {
        repository.findById(jobId).ifPresent(job -> {
            job.setStatus(status);
            job.setUpdatedAt(java.time.Instant.now());
            repository.save(job);
        });
    }
}
