package server;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveRatingServiceImpl implements ReactiveRatingService {
	private ReactiveRatingCrud reviews;

	@Autowired
	public ReactiveRatingServiceImpl(ReactiveRatingCrud reviews) {
		super();
		this.reviews = reviews;
	}

	@Override
	public Mono<Review> create(Review review) {
		int rating = review.getRating();
		if (rating < 1 || rating > 5)
			throw new RuntimeException("rating must be between 1-5");
		return this.reviews.save(review);
	}

	@Override
	public Flux<Review> getAllReviewsByProduct(String productId, String sort) {
		return this.reviews.findAll(Example.of(new Review(null, new Product(productId), null, null, null)),
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByIdByMinRating(String productId, int rating, String sort) {
		return this.reviews.findAllByProductIdAndRatingGreaterThanEqual(productId, rating,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByIdByMaxRating(String productId, int rating, String sort) {
		return this.reviews.findAllByProductIdAndRatingLessThanEqual(productId, rating,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByIdByTimestampFrom(String productId, Instant timestamp, String sort) {
		return this.reviews.findAllByProductIdAndReviewTimestampAfter(productId, timestamp,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByIdByTimestampTo(String productId, Instant timestamp, String sort) {
		return this.reviews.findAllByProductIdAndReviewTimestampBefore(productId, timestamp,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getAllReviewsByReviewer(String email, String sort) {
		return this.reviews.findAll(Example.of(new Review(new Customer(email), null, null, null, null)),
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByReviewerByMinRating(String email, int rating, String sort) {
		return this.reviews.findAllByCustomerEmailAndRatingGreaterThanEqual(email, rating,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByReviewerByMaxRating(String email, int rating, String sort) {
		return this.reviews.findAllByCustomerEmailAndRatingLessThanEqual(email, rating,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByReviewerByTimestampFrom(String email, Instant timestamp, String sort) {
		return this.reviews.findAllByCustomerEmailAndReviewTimestampAfter(email, timestamp,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByReviewerByTimestampTo(String email, Instant timestamp, String sort) {
		return this.reviews.findAllByCustomerEmailAndReviewTimestampBefore(email, timestamp,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getAllReviewsByRatingBetween(int min, int max, String sort) {
		return this.reviews.findAllByRatingBetween(min, max, Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByRatingBetweenByTimestampFrom(int min, int max, Instant timestamp, String sort) {
		return this.reviews.findAllByRatingBetweenAndReviewTimestampAfter(min, max, timestamp,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Flux<Review> getReviewsByRatingBetweenByTimestampTo(int min, int max, Instant timestamp, String sort) {
		return this.reviews.findAllByRatingBetweenAndReviewTimestampBefore(min, max, timestamp,
				Sort.by(Sort.Direction.ASC, sort));
	}

	@Override
	public Mono<Void> cleanup() {
		return this.reviews.deleteAll();
	}
}