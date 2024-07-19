package com.lecture11.assignment1.model;

import com.lecture11.assignment1.model.composite.TitleId;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Title")
public class Title {
    
    @EmbeddedId
    @Column(name = "id")
    private TitleId id;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
    
}
