package com.redis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.demo.entity.Product;
import com.redis.demo.repo.ProductRepo;

@EnableCaching
@RestController
@RequestMapping("/product")
public class Controller {

	@Autowired
	private ProductRepo repo;

	@PostMapping("/save")
	public Product save(@RequestBody Product prod) {
		return repo.save(prod);
	}

	@GetMapping("/get")
	public List<Product> findall() {
		return repo.findall();
	}

	@GetMapping("/get/{id}")
	@Cacheable(key = "#id", value = "Product", unless = "#result.price > 100")
	public Product findbyid(@PathVariable int id) {
		return repo.findbyid(id);
	}

	@PutMapping("/update/{id}")
	public Product update(@RequestBody Product prod, @PathVariable int id) {
		return repo.update(prod, id);
	}

	@DeleteMapping("delete/{id}")
	@CacheEvict(key="id",value = "Product")
	public String deletebyid(@PathVariable int id) {
		return repo.deletebyid(id);
	}
}
