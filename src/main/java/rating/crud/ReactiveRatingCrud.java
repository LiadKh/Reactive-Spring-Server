package rating.crud;

import java.time.Instant;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import rating.entities.Review;
import reactor.core.publisher.Flux;

public interface ReactiveRatingCrud extends ReactiveMongoRepository<Review, String> {
	public Flux<Review> findAllByProductIdAndRatingGreaterThanEqual(String id, int rating, Sort sort);
	public Flux<Review> findAllByProductIdAndRatingLessThanEqual(String id, int rating, Sort sort);
	public Flux<Review> findAllByProductIdAndReviewTimestampAfter(String id, Instant Date, Sort sort);
	public Flux<Review> findAllByProductIdAndReviewTimestampBefore(String id, Instant Date, Sort sort);
	
	public Flux<Review> findAllByCustomerEmailAndRatingGreaterThanEqual(String email, int rating, Sort sort);
	public Flux<Review> findAllByCustomerEmailAndRatingLessThanEqual(String email, int rating, Sort sort);
	public Flux<Review> findAllByCustomerEmailAndReviewTimestampAfter(String email, Instant Date, Sort sort);
	public Flux<Review> findAllByCustomerEmailAndReviewTimestampBefore(String email, Instant Date, Sort sort);
	
	public Flux<Review> findAllByRatingBetween(int min, int max, Sort sort);
	public Flux<Review> findAllByRatingBetweenAndReviewTimestampAfter(int min, int max, Instant Date, Sort sort);
	public Flux<Review> findAllByRatingBetweenAndReviewTimestampBefore(int min, int max, Instant Date, Sort sort);
}