package demo.vote;

import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long>{

}
