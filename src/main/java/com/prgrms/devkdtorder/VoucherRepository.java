package com.prgrms.devkdtorder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    void save(Voucher voucher);

    List<Voucher> findAll();
}
