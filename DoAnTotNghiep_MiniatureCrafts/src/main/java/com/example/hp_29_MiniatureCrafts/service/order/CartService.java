package com.example.hp_29_MiniatureCrafts.service.order;


import com.example.hp_29_MiniatureCrafts.dto.CartItemDTO;
import com.example.hp_29_MiniatureCrafts.entity.CartItem;
import com.example.hp_29_MiniatureCrafts.repository.order.CartRepository;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<CartItemDTO> findAll(Pageable pageable, Long customer_id) {
        Page<CartItem> cartItems = cartRepository.findAllCart(pageable, customer_id);

        return cartItems.map(cartItem -> mapEntityToDTO(cartItem));
    }

    public void save(List<CartItemDTO> cartItemDTOs) {
        for (CartItemDTO cartItemDTO : cartItemDTOs) {
            CartItem cartItem = mapDTOToEntity(cartItemDTO);
            cartRepository.save(cartItem);
        }
    }

    public void updateCartQuantity(Long userId, Long variationId, int newQuantity) {
        List<CartItem> cartItems = cartRepository.findAllCartByUserAndVariation(userId ,variationId );
        for (CartItem cartItem : cartItems) {
            cartItem.setQuantity(newQuantity);
            cartRepository.save(cartItem);
        }
    }

    public CartItemDTO mapEntityToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuality(cartItem.getQuantity());
        cartItemDTO.setVariation_id(variationService.mapVariationToVariationDTO(cartItem.getVariation_id()));
        cartItemDTO.setCustomer_id(orderService.mapCustomerEntityToDTO(cartItem.getCustomer_id()));
        return cartItemDTO;
    }


    public CartItem mapDTOToEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setQuantity(cartItemDTO.getQuality());
        cartItem.setVariation_id(variationService.mapVariationDTOtoVariation(cartItemDTO.getVariation_id()));
        cartItem.setCustomer_id(orderService.mapCustomerDTOToEntity(cartItemDTO.getCustomer_id()));
        return cartItem;
    }

}

