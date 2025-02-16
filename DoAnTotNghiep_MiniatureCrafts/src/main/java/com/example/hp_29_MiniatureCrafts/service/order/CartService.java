package com.example.hp_29_MiniatureCrafts.service.order;

import com.example.hp_29_MiniatureCrafts.dto.CartItemDTO;
import com.example.hp_29_MiniatureCrafts.entity.CartItem;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.repository.order.CartRepository;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private VariationService variationService;

    @Autowired
    private OrderService orderService;


    //user them vao gio hang thi cart se khoa so luong
    //

    public List<CartItemDTO> findAll(Pageable pageable, Long customerId) {
        List<CartItem> cartItems = cartRepository.findByCustomerId(customerId);
        return cartItems.stream().map(this::mapEntityToDTO).toList();
    }
//
//    public CartItemDTO updateQuantity(Long cartItemId, int newQuantity) {
//        CartItem cartItem = cartRepository.findById(cartItemId)
//                .orElseThrow(() -> new RuntimeException("CartItem not found"));
//        cartItem.setQuantity(newQuantity);
//        return mapEntityToDTO(cartRepository.save(cartItem));
//    }

    public ResponseEntity<String> addToCart(CartItemDTO cartItemDTO) {
        Long customerId = cartItemDTO.getCustomer_id().getID();
        Long variationId = cartItemDTO.getVariation_id().getID();

        Variation variation = variationService.findByIDEntity(variationId);
        if (variation == null) {
            throw new RuntimeException("Không tìm thấy Variation với ID: " + variationId);
        }

        int availableQuantity = variation.getQuantity();
        int requestedQuantity = cartItemDTO.getQuantity();

        if (requestedQuantity > availableQuantity) {
//            throw new RuntimeException("Số lượng trong kho không đủ cho sản phẩm: " + variation.getProductID().getName());
            return ResponseEntity.badRequest().body("Not enough quantity: " + variation.getProductID().getName());
        }
        //gan du lieu cho cart
        CartItem entity = new CartItem();
        entity.setCustomer_id(orderService.mapCustomerDTOToEntity(cartItemDTO.getCustomer_id()));
        entity.setVariation_id(variation);
        entity.setQuantity(requestedQuantity);
        entity.setStatus(1);


        CartItem cartItem = cartRepository.findByCustomerAndVariation(customerId, variationId);
        if (cartItem == null) {
            // Create new cart item

            cartRepository.save(entity);
        } else {
            // Update existing cart item
            int newQuantity = cartItem.getQuantity() + requestedQuantity;
            if (newQuantity > availableQuantity) {
                if (cartItemDTO.getVariation_id() == null || cartItemDTO.getVariation_id().getID() == null) {
                    throw new RuntimeException("Variation ID is null");
                }
                return ResponseEntity.badRequest().body("Not enough quantity: " + variation.getProductID().getName());
            }
            cartItem.setQuantity(newQuantity);
            cartRepository.save(cartItem);
        }

        return ResponseEntity.ok("Add to cart successfully");
    }

    public ResponseEntity<String> updateQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        Variation variation = cartItem.getVariation_id();
        int availableQuantity = variation.getQuantity();

        if (newQuantity > availableQuantity) {
            return ResponseEntity.badRequest().body("Not enough stock for: " + variation.getProductID().getName());
        }

        if (newQuantity <= 0) {
            cartRepository.delete(cartItem);
            return ResponseEntity.ok("Cart item removed successfully");
        }

        cartItem.setQuantity(newQuantity);
        cartRepository.save(cartItem);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    public ResponseEntity<String> deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartRepository.delete(cartItem);
        return ResponseEntity.ok("Cart item removed successfully");
    }

    public CartItemDTO mapEntityToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setVariation_id(variationService.mapVariationToVariationDTO(cartItem.getVariation_id()));
        cartItemDTO.setCustomer_id(orderService.mapCustomerEntityToDTO(cartItem.getCustomer_id()));
        return cartItemDTO;
    }

    public CartItem mapDTOToEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setVariation_id(variationService.mapVariationDTOtoVariation(cartItemDTO.getVariation_id()));
        cartItem.setCustomer_id(orderService.mapCustomerDTOToEntity(cartItemDTO.getCustomer_id()));
        return cartItem;
    }
}