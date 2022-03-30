package com.wahyusudrajat.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	private final ProductRepository productRepository;
	
	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) 
			throws ResourceNotFoundException {
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Product id " + productId + " is not found!"));
		return ResponseEntity.ok().body(product);
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product result = productRepository.save(product);
		// echo the request and enriched it with status
		result.setStatus("INSERTED");
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(
			@PathVariable(value = "id") Long productId,
			@RequestBody Product productDetails) throws ResourceNotFoundException  {
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Product id " + productId + " is not found!"));
		
		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		
		Product updatedProduct = productRepository.save(product);
		
		// echo the request and enriched it with status
		updatedProduct.setStatus("UPDATED");
		return ResponseEntity.ok(updatedProduct);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") Long productId) 
			throws ResourceNotFoundException  {
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Product id " + productId + " is not found!"));
		
		productRepository.delete(product);
		// echo the request and enriched it with status
		product.setStatus("DELETED");
		return ResponseEntity.accepted().body(product);
	}
	
}