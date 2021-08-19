package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;

import java.util.*;


public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Collection<Voucher> findAllVoucher();
}