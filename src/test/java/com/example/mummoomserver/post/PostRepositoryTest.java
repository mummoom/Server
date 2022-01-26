package com.example.mummoomserver.post;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장(){
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getTitle());
        assertThat(post.getContent()).isEqualTo(content);
    }
}
