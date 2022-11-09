package com.redis.demo.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.redis.demo.entity.Product;

@Repository
public class ProductRepo {

	public static final String HASH_KEY = "Product";
	
	@Autowired
	private RedisTemplate template;

	public Product save(Product prod) {
		template.opsForHash().put(HASH_KEY, prod.getId(), prod);

		return prod;
	}

	public List<Product> findall() {
		return template.opsForHash().values(HASH_KEY);
	}

	public Product findbyid(int id) {
		System.out.println("called from Db");
		return (Product) template.opsForHash().get(HASH_KEY, id);
	}

	public Product update(Product prod,int id) {
		Product exist = findbyid(id);
		exist.setName(prod.getName());
		exist.setQty(prod.getQty());
		exist.setPrice(prod.getPrice());
		save(exist);
		return (Product) template.opsForHash().get(HASH_KEY,exist);
	}
	public String deletebyid(int id) {
		template.opsForHash().delete(HASH_KEY, id);
		return "Product  Removed";
	}
}
