package com.example.encoding.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "video_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String inputUrl;
    private String outputUrl;
    private String status; // QUEUED, PROCESSING, DONE, FAILED
    private Instant createdAt;
    private Instant updatedAt;
}
