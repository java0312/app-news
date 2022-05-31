package uz.pdp.appnewssiteindependent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnewssiteindependent.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByTitleOrUrl(String title, String url);

    boolean existsByTitleOrUrlAndIdNot(String title, String url, Long id);

}
