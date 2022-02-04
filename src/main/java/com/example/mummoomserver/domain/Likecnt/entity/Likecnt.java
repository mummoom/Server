package com.example.mummoomserver.domain.Likecnt.entity;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.login.users.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Likecnt {

    @ApiModelProperty(example = "좋아하는 게시글 목록 Idx")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likecntIdx;

    @ApiModelProperty(example = "UserIdx 불러옴")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userIdx", referencedColumnName = "userIdx")
    private User userIdx;

    @ApiModelProperty(example = "게시글 Idx 불러옴")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postIdx", referencedColumnName = "postIdx")
    private Post postIdx;

    @ApiModelProperty(example = "상태 코드")
    @Column
    @ColumnDefault("'active'")
    private String status;

    @Builder
    public Likecnt( User userIdx,Post postIdx) {
        this.userIdx = userIdx;
        this.postIdx = postIdx;
    }
}
