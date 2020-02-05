package rating.entities;

import java.time.Instant;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {
	private String id;
	private Customer customer;
	private Product product;
	private Integer rating;
	private Map<String,Object> reviewContent;
	private Instant reviewTimestamp;

	public Review() {

	}

	public Review(Customer customer, Product product, Integer rating, Map<String,Object> reviewContent,
			Instant reviewTimestamp) {
		super();
		this.customer = customer;
		this.product = product;
		this.rating = rating;
		this.reviewContent = reviewContent;
		this.reviewTimestamp = reviewTimestamp;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Map<String,Object> getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(Map<String,Object> reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Instant getReviewTimestamp() {
		return reviewTimestamp;
	}

	public void setReviewTimestamp(Instant reviewTimestamp) {
		this.reviewTimestamp = reviewTimestamp;
	}
}