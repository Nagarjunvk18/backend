package com.news.entity;
import java.util.List;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "newspaper")
public class Newspaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newspaperId;
    
    private String newspaperName;
    
    private String newspaperDescription;
         
    private String newspaperContact;
    
    
}
