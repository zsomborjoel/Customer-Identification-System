package com.company.customeridentificationsystem.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "execution_status")
public class ExecutionStatus {

    @Id
    @GeneratedValue(generator="EX_STAT_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "status")
    private String status;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "start_time")
    private Instant startTime;

}
