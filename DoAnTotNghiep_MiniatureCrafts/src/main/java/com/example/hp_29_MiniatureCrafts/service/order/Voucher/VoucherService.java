package com.example.hp_29_MiniatureCrafts.service.order.Voucher;


import com.example.hp_29_MiniatureCrafts.dto.VoucherDTO;
import com.example.hp_29_MiniatureCrafts.entity.Voucher;
import com.example.hp_29_MiniatureCrafts.repository.order.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    public List<VoucherDTO> getAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        for (Voucher voucher : vouchers) {
            System.out.println("Voucher: " + voucher.getCode());
        }
        return vouchers.stream().map(voucher -> new VoucherDTO(voucher)).collect(Collectors.toList());
    }

    public VoucherDTO findByCode(String code) {
        Voucher voucher = voucherRepository.findVoucherByCode(code);
        return new VoucherDTO(voucher);
    }

    public VoucherDTO findVoucherByCodeVoucher(String code) {
        Voucher voucher = voucherRepository.findVoucherByCodeVoucher(code);
        return new VoucherDTO(voucher);
    }
}
