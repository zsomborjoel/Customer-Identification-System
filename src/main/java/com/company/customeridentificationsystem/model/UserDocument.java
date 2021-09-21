package com.company.customeridentificationsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_document")
public class UserDocument {

    @Id
    @GeneratedValue(generator="DOCUMENT_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_type")
    private String documentType;

    @Lob
    @Column(name = "data")
    private byte[] data;

}
