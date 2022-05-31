package uz.pdp.appnewssiteindependent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnewssiteindependent.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {



}
