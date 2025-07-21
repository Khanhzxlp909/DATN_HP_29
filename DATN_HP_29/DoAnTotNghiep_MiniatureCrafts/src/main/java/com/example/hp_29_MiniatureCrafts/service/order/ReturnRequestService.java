package com.example.hp_29_MiniatureCrafts.service.order;

import com.example.hp_29_MiniatureCrafts.dto.ReturnRequestDTO;
import com.example.hp_29_MiniatureCrafts.dto.ReturnResponseDTO;
import com.example.hp_29_MiniatureCrafts.entity.Customer;
import com.example.hp_29_MiniatureCrafts.entity.OrderLine;
import com.example.hp_29_MiniatureCrafts.entity.ReturnRequest;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.repository.auth.CustomerRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.OrderLineRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.ReturnRequestRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnRequestService {


    @Autowired
    private ReturnRequestRepository returnRepo;

    @Autowired
    private OrderLineRepository orderLineRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private VariationRepository variationRepo;

    /**
     * Tạo yêu cầu trả hàng từ DTO
     */
    public ReturnResponseDTO createRequest(ReturnRequestDTO dto) {
        OrderLine orderLine = orderLineRepo.findById(dto.getOrderLineId())
                .orElseThrow(() -> new EntityNotFoundException("OrderLine not found"));

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        ReturnRequest request = toEntity(dto, orderLine, customer);
        ReturnRequest saved = returnRepo.save(request);
        return toDTO(saved);
    }


    /**
     * Lấy tất cả yêu cầu trả hàng
     */
    public List<ReturnResponseDTO> getAllRequests() {
        return returnRepo.findAll()
                .stream()
                .map(request -> new ReturnResponseDTO(
                        request.getId(),
                        request.getOrderLineID().getID(),
                        request.getCustomerID().getID(),
                        request.getReason(),
                        request.getRequestDate(),
                        request.getStatus(),
                        request.getAdminNote()
                ))
                .collect(Collectors.toList());
    }


    /**
     * Lấy yêu cầu theo khách hàng
     */
    public List<ReturnResponseDTO> getByCustomer(Long customerId) {
        return returnRepo.findByCustomerId(customerId)
                .stream()
                .map(request -> new ReturnResponseDTO(
                        request.getId(),
                        request.getOrderLineID().getID(),
                        request.getCustomerID().getID(),
                        request.getReason(),
                        request.getRequestDate(),
                        request.getStatus(),
                        request.getAdminNote()
                ))
                .collect(Collectors.toList());
    }


    /**
     * Cập nhật trạng thái xử lý yêu cầu trả hàng
     */
    public ReturnResponseDTO updateStatus(Long id, int status, String adminNote) {
        ReturnRequest req = returnRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReturnRequest not found"));

        req.setStatus(status);
        req.setAdminNote(adminNote);
        ReturnRequest updated = returnRepo.save(req);
        return toDTO(updated);
    }

    public ReturnResponseDTO getById(Long id) {
        ReturnRequest request = returnRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReturnRequest not found"));
        return toDTO(request);
    }

    public void deleteById(Long id) {
        ReturnRequest request = returnRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReturnRequest not found"));
        request.setStatus(4); // hoặc thêm 1 trường boolean deleted
        returnRepo.save(request);
    }

    public List<ReturnResponseDTO> getByStatus(int status) {
        return returnRepo.findByStatus(status)
                .stream()
                .map(ReturnRequestService::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReturnResponseDTO> getByDateRange(LocalDateTime from, LocalDateTime to) {
        return returnRepo.findByRequestDateBetween(from, to)
                .stream()
                .map(ReturnRequestService::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReturnResponseDTO completeReturn(Long id) {
        ReturnRequest request = returnRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReturnRequest not found"));

        if (request.getStatus() != 1) { // chưa approved thì không cho hoàn
            throw new IllegalStateException("Return must be approved before completion");
        }

        request.setStatus(3); // COMPLETED

        // cập nhật lại kho:
        OrderLine orderLine = request.getOrderLineID();
        Variation variation = orderLine.getVariationID();
        variation.setQuantity(variation.getQuantity() + orderLine.getQuantity());

        variationRepo.save(variation);
        returnRepo.save(request);

        return toDTO(request);
    }


    public static ReturnRequest toEntity(ReturnRequestDTO dto, OrderLine orderLine, Customer customer) {
        ReturnRequest entity = new ReturnRequest();
        entity.setOrderLineID(orderLine);
        entity.setCustomerID(customer);
        entity.setReason(dto.getReason());
        entity.setStatus(0);
        entity.setRequestDate(LocalDateTime.now());
        return entity;
    }

    public static ReturnResponseDTO toDTO(ReturnRequest entity) {
        return new ReturnResponseDTO(
                entity.getId(),
                entity.getOrderLineID().getID(),
                entity.getCustomerID().getID(),
                entity.getReason(),
                entity.getRequestDate(),
                entity.getStatus(),
                entity.getAdminNote()
        );
    }
}
