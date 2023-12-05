package com.lcwd.electronic.store.electronicStore.controller;

import com.lcwd.electronic.store.electronicStore.dtos.ImageResponse;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicStore.dtos.UserDto;
import com.lcwd.electronic.store.electronicStore.services.FileService;
import com.lcwd.electronic.store.electronicStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static com.lcwd.electronic.store.electronicStore.AppConstanstant.Constantants.*;
import static com.lcwd.electronic.store.electronicStore.AppConstanstant.Constantants.SORT_DIR;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String path;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("getbyid/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = SORT_DIR, required = false) String sortDir
    ) {
        PageableResponse<ProductDto> all = productService.getAll(pageSize, pageNumber, sortBy, sortDir);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/getalllive/{b}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @PathVariable boolean b,
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = SORT_DIR, required = false) String sortDir
    ) {
        PageableResponse<ProductDto> all = productService.getAllLive(b, pageSize, pageNumber, sortBy, sortDir);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/getbytitle/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
            @PathVariable String subTitle, @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = SORT_DIR, required = false) String sortDir
    ) {
        PageableResponse<ProductDto> all = productService.searchProductByTitle(subTitle, pageSize, pageNumber, sortBy, sortDir);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImg(@RequestParam("image") MultipartFile img, @PathVariable String productId) throws IOException {

        String s = fileService.uploadFile(img, path, productId);
        ProductDto productById = productService.getProduct(productId);
        productById.setProductImage(s);
        productService.updateProduct(productById, productId);
        ImageResponse imageResponse = ImageResponse.builder().img(s).massage("sucess").httpStatus(HttpStatus.CREATED).status(true).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);


    }

    @GetMapping("/image/{productId}")
    public void serveImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        ProductDto product = productService.getProduct(productId);

        InputStream resource = fileService.getResource(path,product.getProductImage() );
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }
}
