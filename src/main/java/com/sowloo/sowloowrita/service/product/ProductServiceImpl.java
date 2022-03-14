package com.sowloo.sowloowrita.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dto.ProductDto;
import com.sowloo.sowloowrita.data.models.Product;
import com.sowloo.sowloowrita.data.repository.ProductRepository;
import com.sowloo.sowloowrita.web.exception.BusinessLogicException;
import com.sowloo.sowloowrita.web.exception.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private CloudService cloudService;

    @Override
    public Product findProductById(Long productId) throws ProductDoesNotExistException {

        if (productId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Product> queryResult = productRepository.findById(productId);

        return queryResult.orElseThrow(()->
                new ProductDoesNotExistException("Product with Id:" + productId+ "Does not exist"));

    }

    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Product createProduct(ProductDto productDto) {

        if (productDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Product> query = productRepository.findByName(productDto.getName());
        if (query.isPresent()){
            throw new BusinessLogicException("Product with name " + productDto.getName()+ "already exist");
        }
        Product product = new Product();

//        try {
//            if (productDto.getImage() != null){
//                Map<?,?> uploadResult = cloudService.upload(productDto.getImage().getBytes(), ObjectUtils.asMap(
//                        "public_id",
//                        "inventory/" + productDto.getImage().getOriginalFilename(),
//                        "overwrite", true
//                ));
//                product.setImageUrl(uploadResult.get("url").toString());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductDto productDetails) {

        Product updatedProduct = productRepository.findById(productId).orElseThrow(()->
                 new ProductDoesNotExistException("product with " + productId + "does not exist" ));

        updatedProduct.setName(productDetails.getName());
        updatedProduct.setQuantity(productDetails.getQuantity());
        updatedProduct.setDescription(productDetails.getDescription());
        updatedProduct.setPrice(productDetails.getPrice());

        return productRepository.save(updatedProduct);
    }

    private Product saveOrUpdateProduct(Product product){
        if (product == null){
            throw new BusinessLogicException("Product cannot be null");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProductDetails(Long productId, JsonPatch patch) {
        Optional<Product> productQuery = productRepository.findById(productId);
        if (productQuery.isEmpty()){
            throw new BusinessLogicException("Product with Id" + productId + "Does not exist");
        }
        Product updatedProduct = productQuery.get();

        try{
            updatedProduct = applyPatchToProduct(patch , updatedProduct);
            return saveOrUpdateProduct(updatedProduct);
        }catch (JsonPatchException | JsonProcessingException je){
            throw new BusinessLogicException("Product update Failed");
        }

    }

    private Product applyPatchToProduct(JsonPatch patch, Product updatedProduct) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched =  patch.apply(objectMapper.convertValue(updatedProduct, JsonNode.class));

        return objectMapper.treeToValue(patched, Product.class);
    }
}


// CRUD OPERATIONS   C - POST ,,,   R - GET ,,,, U - PUT & PATCH ,,,, D- DELETE