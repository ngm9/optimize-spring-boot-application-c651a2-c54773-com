package com.example.encoding.controller;

import com.example.encoding.model.VideoJob;
import com.example.encoding.service.VideoJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final VideoJobService service;
    public JobController(VideoJobService service) {
        this.service = service;
    }
    @PostMapping
    public CompletableFuture<ResponseEntity<VideoJob>> submit(@RequestBody VideoJob videoJob) {
        return service.submitJob(videoJob).thenApply(ResponseEntity::ok);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoJob> getJob(@PathVariable Long id) {
        return service.getJob(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<VideoJob> listJobs() {
        return service.allJobs();
    }
    @GetMapping("/status/stats")
    public Map<String, Long> stats() {
        return service.statusStats();
    }
}
