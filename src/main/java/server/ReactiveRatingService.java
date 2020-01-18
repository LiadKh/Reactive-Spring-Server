package server;

import java.time.Instant;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveRatingService {
	public Mono<Review> create(Review review);

	public Flux<Review> getAllReviewsByProduct(String productId, String sort);

	public Flux<Review> getAllReviewsByReviewer(String email, String sort);

	public Flux<Review> getAllReviewsByRatingBetween(int min, int max, String sort);

	public Flux<Review> getReviewsByIdByMinRating(String productId, int valueOf, String sort);

	public Flux<Review> getReviewsByIdByMaxRating(String productId, int valueOf, String sort);

	public Flux<Review> getReviewsByIdByTimestampFrom(String productId, Instant timestamp, String sort);

	public Flux<Review> getReviewsByIdByTimestampTo(String productId, Instant timestamp, String sort);
	
	public Flux<Review> getReviewsByReviewerByMinRating(String email, int valueOf, String sort);

	public Flux<Review> getReviewsByReviewerByMaxRating(String email, int valueOf, String sort);

	public Flux<Review> getReviewsByReviewerByTimestampFrom(String email, Instant timestamp, String sort);

	public Flux<Review> getReviewsByReviewerByTimestampTo(String email, Instant timestamp, String sort);
	
	public Flux<Review> getReviewsByRatingBetweenByTimestampFrom(int mix, int max , Instant timestamp, String sort);

	public Flux<Review> getReviewsByRatingBetweenByTimestampTo(int mix, int max, Instant timestamp, String sort);

	public Mono<Void> cleanup();
}