package org.prgms.voucheradmin.domain.voucher.dto;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

public class VoucherUpdateReqDto {
    private UUID voucherId;
    private VoucherType voucherType;
    private long amount;

    public VoucherUpdateReqDto(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}