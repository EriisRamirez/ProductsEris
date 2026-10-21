package com.example.products.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.products.dto.PorcentajeRequest;
import com.example.products.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.products.dto.ProductRequest;
import com.example.products.dto.ProductResponse;
import com.example.products.model.Product;
import com.example.products.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.web.client.ResourceAccessException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping ()
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> lista = new ArrayList<>();

        for(Product elem: service.findAll()){
            lista.add(toResponse(elem));
        }
        return ResponseEntity.ok(lista) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product =service.findById(id).orElseThrow(
                ()->
                        new ResourceAccessException("Producto no encontrado"+id)
        );
        
        return ResponseEntity.ok(toResponse(product));
    }
    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {
        Product productSaved =service.save(toEntity(product),product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(productSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductRequest productUpdated) {
        Product product1 =service.update(
            id,toEntity( productUpdated),
            productUpdated.categoryId()
        );
        return ResponseEntity.ok(toResponse(product1));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted =service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody PorcentajeRequest request) {

        Product product2 = service.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found whit id: " + id)
        );

        // Se agrega 'product2' como tercer argumento
        Product productact = service.descuentoProduct(id, request.getDescuento().doubleValue(), product2);

        return ResponseEntity.ok(toResponse(productact));
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getStock(),"",0L);
    }

    private Product toEntity(ProductRequest product){
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (first, second) -> first
                ));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }
}
