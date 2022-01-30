package com.example.mummoomserver.domain.Dog.entity;
import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.login.users.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity

@Table
public class Dog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dogIdx")
    private Long dogIdx;

    @Column(name="dogName", nullable = false)
    private String dogName;

    @Column(name="dogBirth", nullable = false)
    private String dogBirth;

    @Column(name="dogType",nullable = false)
    private String dogType;

    @Column(name="dogSex", nullable = false)
    @ColumnDefault("'0'")
    private String dogSex;

    @Column(name="surgery", nullable = false)

    @ColumnDefault("'Y'")
    private String surgery;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name="id")
    private User user;

    @Column(name="status", nullable = false)

    @ColumnDefault("'active'")
    private String status;

    @Builder
    public Dog(String dogName, String dogBirth, String dogType, String dogSex, String surgery) {
        this.dogName = dogName;
        this.dogBirth = dogBirth;
        this.dogType = dogType;
        this.dogSex = dogSex;
        this.surgery = surgery;
    }

    public void update(String dogName, String dogBirth, String dogType, String dogSex, String surgery) {
        this.dogName = dogName;
        this.dogBirth = dogBirth;
        this.dogType = dogType;
        this.dogSex = dogSex;
        this.surgery = surgery;
    }
    public void delete(){
        this.status = "delete";
    }

}
