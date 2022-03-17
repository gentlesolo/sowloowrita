package com.sowloo.sowloowrita.web.controller;


import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dto.ApiResponse;
import com.sowloo.sowloowrita.data.dto.ProductDto;
import com.sowloo.sowloowrita.data.models.Product;
import com.sowloo.sowloowrita.service.product.ProductService;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping()
    public ResponseEntity<?> findAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
        //return ResponseEntity.ok().body(products);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto){
        try {
            Product product = productService.createProduct(productDto);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch (ProductDoesNotExistException | BusinessLogicException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?>findProduct(@PathVariable Long productId){
        try {
            return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK);
        }catch (ProductDoesNotExistException e){
            return new ResponseEntity<>(new ApiResponse(false, "Product does not exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?>updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDetails){

        try{
            return new ResponseEntity<>(productService.updateProduct(productId, productDetails),HttpStatus.OK );
        }catch (ProductDoesNotExistException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{productId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateMyProduct(@PathVariable Long productId, @RequestBody JsonPatch patch){
        try{
            Product updatedProduct = productService.updateProductDetails(productId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }catch (BusinessLogicException ex){
          return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

/*[

for postman to make a patch request
{
    "op": "replace", "path" : "/description" , "value" : "This is a good des"
},

{
    "op" : "replace" , "path" : "/price" , "value" : 890
}

]*/
