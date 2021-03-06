package com.github.bitcoin.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "addresses")
@ToString(exclude = "addresses")
@Table(name = "accounts", schema = "public")
public class Account implements Serializable, Cloneable {

    private static final long serialVersionUID = 3608765672379710671L;

    @Id
    @Column(
            name = "ID"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private UUID userId;

    @Column(
            name = "mnemonic",
            nullable = false,
            unique = true
    )
    private String mnemonic;

    @Column(
            name = "private_key",
            nullable = false,
            unique = true
    )
    private String privateKey;

    @Column(
            name = "public_key",
            nullable = false,
            unique = true
    )
    private String publicKey;

    @Column(
            name = "chain_code",
            nullable = false,
            unique = true
    )
    private String chainCode;

    @Column(
            name = "time_stamp",
            nullable = false,
            unique = true
    )
    private Long timeStamp;

    @Column(
            name = "amount",
            nullable = false
    )
    private BigInteger amount = BigInteger.ZERO;

    @OneToMany(
            targetEntity = Address.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "address_id",
            foreignKey = @ForeignKey(
                    name = "account_address_fk"
            )
    )
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(
            targetEntity = Transaction.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "transaction_id",
            foreignKey = @ForeignKey(
                    name = "account_transaction_fk"
            )
    )
    private Set<Transaction> transactions = new HashSet<>();

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

    public Account(UUID userId,
                   String mnemonic,
                   String privateKey,
                   String publicKey,
                   String chainCode,
                   Long timeStamp) {
        this.userId = userId;
        this.mnemonic = mnemonic;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.chainCode = chainCode;
        this.timeStamp = timeStamp;
    }

    public Account(String mnemonic,
                   String privateKey,
                   String publicKey,
                   String chainCode,
                   Long timeStamp,
                   List<Address> addresses) {
        this.mnemonic = mnemonic;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.chainCode = chainCode;
        this.timeStamp = timeStamp;
    }

    public void incoming(Transaction transaction, BigInteger value) {
        addTransaction(transaction);
        addAmount(value);
    }

    public void outgoing(BigInteger value, BigInteger change) {
        subtractAmount(value);
        addAmount(change);
    }

    public void addTransaction(Transaction transaction) {
        if (Objects.nonNull(transaction)) {
            this.transactions.add(transaction);
        }
    }

    public void addAmount(Long value) {
        if (Objects.nonNull(value)) {
            this.amount = this.amount.add(BigInteger.valueOf(value));
        }
    }

    public void addAmount(BigInteger value) {
        if (Objects.nonNull(value)) {
            this.amount = this.amount.add(value);
        }
    }

    public void subtractAmount(BigInteger value) {
        if (Objects.nonNull(value)) {
            this.amount = this.amount.subtract(value);
        }
    }

    public void addAddresses(List<Address> addresses) {
        this.addresses.addAll(addresses);
    }

}
