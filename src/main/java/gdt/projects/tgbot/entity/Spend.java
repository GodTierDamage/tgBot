package gdt.projects.tgbot.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "SPEND")
public class Spend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHAT_ID")
    private Long chat_id;

    @Column(name = "SPEND")
    private BigDecimal spend;

    @Column(name = "TIME_OF_OPERATION")
    private Timestamp timeOfOperation;
}
