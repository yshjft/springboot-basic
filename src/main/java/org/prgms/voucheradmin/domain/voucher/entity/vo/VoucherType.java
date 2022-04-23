package org.prgms.voucheradmin.domain.voucher.entity.vo;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FIXED_AMOUNT"),
    PERCENTAGE_DISCOUNT("2", "PERCENTAGE_DISCOUNT");

    private final String voucherTypeId;
    private final String typeName;
    private static final Map<String, VoucherType> voucherTypes =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(voucherType -> voucherType.voucherTypeId, Function.identity())));

    VoucherType(String voucherTypeId, String typeName) {
        this.voucherTypeId = voucherTypeId;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return String.format("%s. %s", voucherTypeId, typeName);
    }

    public static Optional<VoucherType> findVoucherType(String selectedVoucherTypeId) {
        return Optional.ofNullable(voucherTypes.get(selectedVoucherTypeId));
    }
}
