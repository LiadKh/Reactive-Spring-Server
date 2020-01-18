package server;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveRatingController {
	private ReactiveRatingService reviews;

	@Autowired
	public ReactiveRatingController(ReactiveRatingService reviews) {
		super();
		this.reviews = reviews;
	}

	private Review toEntity(ReviewBoundary review) {
		try {
			Review rv = new Review();
			rv.setCustomer(review.getCustomer());
			rv.setProduct(review.getProduct());
			rv.setRating(review.getRating());
			rv.setReviewContent(review.getReviewContent());
			rv.setReviewTimestamp(createTimeStamp());
			return rv;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* Create customer review -- POST -- returns MONO */
	@RequestMapping(path = "/reviews", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Review> create(@RequestBody ReviewBoundary reviewBoundary) {
		try {
			return this.reviews.create(toEntity(reviewBoundary));
		} catch (Exception e) {
			throw new BadReqException(e.getMessage());
		}
	}

	private enum ReviewsFilter {
		byMinRating, byMaxRating, byTimestampFrom, byTimestampTo
	}

	private enum ReviewsFilterByTimestamp {
		byTimestampFrom, byTimestampTo
	}

	/*
	 * Get All reviews byProduct filtering -- GET -- returns FLUX
	 */
	@RequestMapping(path = "/reviews/byProduct/{productId}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Review> getAllReviewsByProductByFilter(@PathVariable("productId") String productId,

			@RequestParam(name = "filterType", required = false) ReviewsFilter filterType,

			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,

			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sort) {

		if (filterType == null) {
			return this.reviews.getAllReviewsByProduct(productId, sort);
		} else {
			try {
				if (filterValue.isEmpty())
					throw new RuntimeException("Value to search is empty");

				switch (filterType) {

				/* Get All reviews byProduct by filtering by MinRating -- GET -- returns FLUX */
				case byMinRating:
					return this.reviews.getReviewsByIdByMinRating(productId, Integer.parseInt(filterValue), sort);

				/* Get All reviews byProduct by filtering by MaxRating -- GET -- returns FLUX */
				case byMaxRating:
					return this.reviews.getReviewsByIdByMaxRating(productId, Integer.parseInt(filterValue), sort);

				/*
				 * Get All reviews byProduct by filtering by TimestampFrom -- GET -- returns
				 * FLUX
				 */

				case byTimestampFrom:
					return this.reviews.getReviewsByIdByTimestampFrom(productId, Instant.parse(filterValue), sort);

				/*
				 * Get All reviews byProduct by filtering by TimestampTo -- GET -- returns FLUX
				 */

				case byTimestampTo:
					return this.reviews.getReviewsByIdByTimestampTo(productId, Instant.parse(filterValue), sort);

				default:
					throw new RuntimeException("can't search by this type " + filterType);
				}

			} catch (Exception e) {
				throw new BadReqException(e.getMessage());
			}
		}
	}

	/* Get All reviews byReviewer filtering--GET--returns FLUX */

	@RequestMapping(path = "/reviews/byReviewer/{email}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)

	public Flux<Review> getAllReviewsByReviewerByFilter(@PathVariable("email") String email,

			@RequestParam(name = "filterType", required = false) ReviewsFilter filterType,

			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,

			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sort) {

		if (filterType == null) {
			return this.reviews.getAllReviewsByReviewer(email, sort);
		} else {
			try {
				if (filterValue.isEmpty())
					throw new RuntimeException("Value to search is empty");

				switch (filterType) {

				/*
				 * Get All reviews byReviewer by filtering by MinRating -- GET -- returns FLUX
				 */

				case byMinRating:
					return this.reviews.getReviewsByReviewerByMinRating(email, Integer.parseInt(filterValue), sort);

				/*
				 * Get All reviews byReviewer by filtering by MaxRating -- GET -- returns FLUX
				 */

				case byMaxRating:
					return this.reviews.getReviewsByReviewerByMaxRating(email, Integer.parseInt(filterValue), sort);

				/*
				 * Get All reviews byReviewer by filtering by TimestampFrom -- GET -- returns
				 * FLUX
				 */

				case byTimestampFrom:
					return this.reviews.getReviewsByReviewerByTimestampFrom(email, Instant.parse(filterValue), sort);

				/*
				 * Get All reviews byReviewer by filtering by TimestampTo -- GET -- returns FLUX
				 */

				case byTimestampTo:
					return this.reviews.getReviewsByReviewerByTimestampTo(email, Instant.parse(filterValue), sort);

				default:
					throw new RuntimeException("can't search by this type " + filterType);
				}

			} catch (Exception e) {
				throw new BadReqException(e.getMessage());
			}
		}
	}

	/* Get All reviews byRating Between filtering -- GET -- returns FLUX */

	@RequestMapping(path = "/reviews/byRatingBetween/{minRatingInclusive}/{maxRatingInclusive}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Review> getAllReviewsByRatingByFilter(@PathVariable("minRatingInclusive") int min,

			@PathVariable("maxRatingInclusive") int max,

			@RequestParam(name = "filterType", required = false) ReviewsFilterByTimestamp filterType,

			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,

			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sort) {

		if (filterType == null) {
			return this.reviews.getAllReviewsByRatingBetween(min, max, sort);
		} else {
			try {
				if (filterValue.isEmpty())
					throw new RuntimeException("Value to search is empty");

				switch (filterType) {

				/*
				 * Get All reviews byRating Between by filtering by TimestampFrom -- GET --
				 * returns FLUX
				 */

				case byTimestampFrom:
					return this.reviews.getReviewsByRatingBetweenByTimestampFrom(min, max, Instant.parse(filterValue),
							sort);

				/*
				 * Get All reviews byRating Between by filtering by TimestampTo -- GET --
				 * returns FLUX
				 */

				case byTimestampTo:
					return this.reviews.getReviewsByRatingBetweenByTimestampTo(min, max, Instant.parse(filterValue),
							sort);

				default:
					throw new RuntimeException("can't search by this type " + filterType);
				}

			} catch (Exception e) {
				throw new BadReqException(e.getMessage());
			}
		}
	}

	/* Delete all reviews -- DELETE -- returns MONO */
	@RequestMapping(path = "/reviews", method = RequestMethod.DELETE)
	public Mono<Void> cleanup() {
		return this.reviews.cleanup();
	}

	public Instant createTimeStamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp.toInstant();
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Mono<Map<String,String>> handleException(BadReqException e) {
		return Mono.just(Collections.singletonMap("error", e.getMessage()));
	}
}