package com.krish.demo.controller;

import com.krish.demo.entity.Product;
import com.krish.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/welcome")
    public String greeting()
    {
        return "Welcome!!! Endpoint with no security";
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long productId)
    {
        return productService.getProduct(productId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }


}
