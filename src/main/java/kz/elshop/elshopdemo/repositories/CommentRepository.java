package kz.elshop.elshopdemo.repositories;

import kz.elshop.elshopdemo.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByItem_id(Long id);
}
