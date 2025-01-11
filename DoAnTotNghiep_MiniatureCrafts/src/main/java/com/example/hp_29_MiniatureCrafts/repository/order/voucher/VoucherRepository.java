package com.example.hp_29_MiniatureCrafts.repository.order.voucher;

import com.example.hp_29_MiniatureCrafts.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherRepository extends JpaRepository<Voucher,Integer> {

    @Query("select v from Voucher v where v.Code =:codevoucher")
    Voucher findVoucherByCode(@Param("codevoucher")String codevoucher);


    @Query("select v from Voucher v where v.Code LIKE %:codevoucher%")
    Voucher findVoucherByCodeVoucher(@Param("codevoucher")String codevoucher);
}
