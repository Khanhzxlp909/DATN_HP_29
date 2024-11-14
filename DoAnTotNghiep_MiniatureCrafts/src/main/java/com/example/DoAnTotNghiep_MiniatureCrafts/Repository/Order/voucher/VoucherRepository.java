package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.voucher;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherRepository extends JpaRepository<Voucher,Integer> {

    @Query("select v from Voucher v where v.Code =:codevoucher")
    Voucher findVoucherByCode(@Param("codevoucher")String codevoucher);
}
