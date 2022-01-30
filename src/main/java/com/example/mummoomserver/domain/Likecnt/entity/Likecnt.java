package com.example.mummoomserver.domain.Likecnt.entity;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Entity
public class Likecnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likecntIdx;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userIdx", referencedColumnName = "userIdx")
    private User userIdx;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postIdx", referencedColumnName = "postIdx")
    private Post postIdx;

    @Column
    @ColumnDefault("'active'")
    private String status;

    @Builder
    public Likecnt( User userIdx,Post postIdx) {
        this.userIdx = userIdx;
        this.postIdx = postIdx;
    }
}
