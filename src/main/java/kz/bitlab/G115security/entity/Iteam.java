package kz.bitlab.G115security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ITEMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Iteam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PRODUCT",columnDefinition = "VARCHAR(50)")
    private String product;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;
}

