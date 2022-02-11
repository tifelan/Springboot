package com.phoenix.service.product;

import com.github.fge.jsonpatch.JsonPatch;
import com.phoenix.data.dto.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import org.springframework.data.web.JsonPath;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts() ;
    Product findProductById(Long productId)throws ProductDoesNotExistException;
    Product createProduct(ProductDto productDto) throws BusinessLogicException;
    Product updateProductDetails(Long productId, JsonPatch productPatch) throws BusinessLogicException;

}
