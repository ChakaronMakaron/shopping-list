package com.lemakhno.shopping.controllers;

import static java.util.Objects.isNull;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lemakhno.shopping.constants.Endpoints;
import com.lemakhno.shopping.constants.Views;
import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.entities.PurchaseOptionEntity;
import com.lemakhno.shopping.models.Product;
import com.lemakhno.shopping.models.PurchaseOption;
import com.lemakhno.shopping.services.ProductService;
import com.lemakhno.shopping.util.AppUtil;

@Controller
public class ProductController {
    
    private final ProductService productService;
    private final String REDIRECT_TO_PURCHASE_OPTIONS = "redirect:getPurchaseOptions?id=%s";
    private final String REDIRECT_TO_PRODUCTS_LIST = "redirect:list";
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(Endpoints.PRODUCTS_LIST)
    public String showProductsList(Model model) {
        String userEmail = AppUtil.getAuthentication().getName();
        List<ProductEntity> products = productService.getProductsByUserEmail(userEmail);
        model.addAttribute("products", products);
        model.addAttribute("details", userEmail);
        return Views.LIST;
    }

    @GetMapping(Endpoints.ADD_NEW_PRODUCT)
    public String addNewProduct(Model model) {
        model.addAttribute("product", new ProductEntity());
        return Views.NEW_PRODUCT;
    }

    @PostMapping(Endpoints.ADD_NEW_PRODUCT)
    public String processNewProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return REDIRECT_TO_PRODUCTS_LIST;
    }

    @GetMapping(Endpoints.GET_PURCHASE_OPTIONS)
    public String getPurchaseOptions(@RequestParam("id") String id, Model model) {
        ProductEntity product = productService.getProductById(id);
        List<PurchaseOptionEntity> purchaseOptions = product.getPurchaseOptions();
        model.addAttribute("purchaseOptions", isNull(purchaseOptions) ? Collections.emptyList() : purchaseOptions);
        model.addAttribute("productName", product.getName());
        model.addAttribute("productId", id);
        return Views.PURCHASE_OPTIONS;
    }

    @GetMapping(Endpoints.RENAME_PRODUCT)
    public String renameProduct(@RequestParam("id") String id, Model model) {
        ProductEntity product = productService.getProductById(id);
        model.addAttribute("product", product);
        return Views.RENAME_PRODUCT;
    }

    @PostMapping(Endpoints.RENAME_PRODUCT)
    public String renameProduct(@ModelAttribute("product") ProductEntity renamedProduct) {
        productService.renameProductById(renamedProduct.getId(), renamedProduct.getName());
        return REDIRECT_TO_PRODUCTS_LIST;
    }

    @GetMapping(Endpoints.DELETE_PRODUCT)
    public String deleteProduct(@RequestParam("id") String id) {
        productService.deleteProductById(id);
        return REDIRECT_TO_PRODUCTS_LIST;
    }

    @GetMapping(Endpoints.CLEAR_PURCHASE_OPTIONS)
    public String clearPurchaseOptions(@RequestParam("id") String id) {
        productService.clearPurchaseOptionsById(id);
        return REDIRECT_TO_PRODUCTS_LIST;
    }

    @GetMapping(Endpoints.ADD_PURCHASE_OPTION)
    public String addPurchaseOption(@RequestParam("id") String productId, Model model) {
        model.addAttribute("purchaseOption", new PurchaseOptionEntity());
        // For returning back to this product's options
        // from purchase option creation form
        model.addAttribute("id", productId);
        return Views.NEW_PURCHASE_OPTION;
    }

    @PostMapping(Endpoints.ADD_PURCHASE_OPTION)
    public String addPurchaseOption(@ModelAttribute("purchaseOption") PurchaseOption purchaseOption,
                                    @RequestParam("id") String productId) {
        
        productService.addPurchaseOptionToProduct(purchaseOption, productId);
        return String.format(REDIRECT_TO_PURCHASE_OPTIONS, productId);
    }

    @GetMapping(Endpoints.DELETE_PURCHASE_OPTION)
    public String deletePurchaseOption(@RequestParam("id") String productOptionId, @RequestParam("productId") String productId) {
        productService.deletePurchaseOptionById(productOptionId);
        return String.format(REDIRECT_TO_PURCHASE_OPTIONS, productId);
    }
}
