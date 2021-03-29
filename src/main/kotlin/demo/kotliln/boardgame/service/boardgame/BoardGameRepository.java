package demo.kotliln.boardgame.service.boardgame;

import demo.kotliln.boardgame.service.entities.BoardGameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardGameRepository extends CrudRepository<BoardGameEntity, Long> {

    Optional<BoardGameEntity> findByName(String name);

    List<BoardGameEntity> findAll();
}