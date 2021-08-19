package com.prgrms.devkdtorder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("cCan not find a voucher for " + voucherId));
    }

    public UUID saveVoucher(Voucher voucher){
        voucherRepository.save(voucher);
        return voucher.getVoucherId();
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }
}