package server;

import java.util.Map;

import org.springframework.data.annotation.Id;

public class ReviewBoundary {
	private String id;
	private Customer customer;
	private Product product;
	private Integer rating;
	private Map<String,Object> reviewContent;

	public ReviewBoundary() {

	}

	public ReviewBoundary(String id, Customer customer, Product product, Integer rating, Map<String,Object> reviewContent) {
		super();
		this.id = id;
		this.customer = customer;
		this.product = product;
		this.rating = rating;
		this.reviewContent = reviewContent;
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
}