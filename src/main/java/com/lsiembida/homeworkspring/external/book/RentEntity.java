package com.lsiembida.homeworkspring.external.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "rents")
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate rentedFrom;
    private LocalDate rentedTo;
}
