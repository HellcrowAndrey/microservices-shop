package com.github.bitcoin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses", schema = "public")
public class Address implements Serializable, Cloneable {

    private static final long serialVersionUID = -6803221807367926458L;

    @Id
    @Column(
            name = "ID"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "index",
            nullable = false,
            unique = true
    )
    private Integer index;

    @Column(
            name = "address",
            nullable = false,
            unique = true
    )
    private String address;

    @Column(
            name = "amount",
            nullable = false
    )
    private BigInteger amount = BigInteger.ZERO;

    @ManyToOne
    private Account account;

    @OneToMany
    private List<UnspentOut> outs = new ArrayList<>();

    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(value = EnumType.STRING)
    private EntityStatus status = EntityStatus.on;

    @CreationTimestamp
    @Column(
            name = "create_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(
            name = "update_at",
            nullable = false
    )
    private LocalDateTime updateAt;

    public void incoming(UnspentOut out, Long value) {
        if (Objects.nonNull(out) && Objects.nonNull(amount)) {
            this.outs.add(out);
            this.amount = this.amount.add(BigInteger.valueOf(value));
        }
    }

}
