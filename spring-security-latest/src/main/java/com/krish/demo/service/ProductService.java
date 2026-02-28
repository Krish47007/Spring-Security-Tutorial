package com.krish.demo.service;

import com.krish.demo.entity.Product;
import com.krish.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product addProduct(Product product)
    {
        return repository.save(product);
    }

    public List<Product> getAllProducts()
    {
        return repository.findAll();
    }

    public Product getProduct(long productId)
    {
        return repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s doesn't exist",productId)));
    }

}
