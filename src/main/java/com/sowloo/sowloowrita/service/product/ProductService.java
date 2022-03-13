package com.sowloo.sowloowrita.service.product;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ProductDto;
import com.sowloo.sowloowrita.data.models.Product;
import com.sowloo.sowloowrita.web.exception.ProductDoesNotExistException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product findProductById(Long productId)throws ProductDoesNotExistException;

    List<Product> getAllProducts(Pageable pageable);

    Product createProduct(ProductDto productDto);

    Product updateProduct(Long productId, ProductDto productDetails);

    Product updateProductDetails(Long productId, JsonPatch patch);
}
