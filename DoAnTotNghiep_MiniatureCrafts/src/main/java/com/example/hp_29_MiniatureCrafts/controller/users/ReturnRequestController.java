package com.example.hp_29_MiniatureCrafts.controller.users;

import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.dto.ReturnRequestDTO;
import com.example.hp_29_MiniatureCrafts.dto.ReturnResponseDTO;
import com.example.hp_29_MiniatureCrafts.service.order.ReturnRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/returns")
public class ReturnRequestController {

    private final ReturnRequestService returnRequestService;

    public ReturnRequestController(ReturnRequestService returnRequestService) {
        this.returnRequestService = returnRequestService;
    }

    @PostMapping
    public ResponseEntity<ReturnResponseDTO> create(@RequestParam List<POSOrderDTO> order, @RequestBody ReturnRequestDTO dto) {
        ReturnResponseDTO response = returnRequestService.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReturnResponseDTO>> getAll() {
        return ResponseEntity.ok(returnRequestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(returnRequestService.getById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReturnResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(returnRequestService.getByCustomer(customerId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ReturnResponseDTO>> getByStatus(@RequestParam int status) {
        return ResponseEntity.ok(returnRequestService.getByStatus(status));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ReturnResponseDTO>> filterByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(returnRequestService.getByDateRange(from, to));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ReturnResponseDTO> updateStatus(@PathVariable Long id,
                                                          @RequestParam int status,
                                                          @RequestParam(required = false) String adminNote) {
        return ResponseEntity.ok(returnRequestService.updateStatus(id, status, adminNote));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ReturnResponseDTO> complete(@PathVariable Long id) {
        return ResponseEntity.ok(returnRequestService.completeReturn(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        returnRequestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

