package org.prgms.voucheradmin.domain.voucher.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.FIXED_AMOUNT;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.PERCENTAGE_DISCOUNT;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.global.exception.customexception.VoucherNotFoundException;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    CustomerRepository customerRepository;

    @Test
    @DisplayName("바우처 생성 테스트")
    void testVoucherCreation() {
        // when
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
        when(voucherRepository.create(any(Voucher.class))).thenReturn(voucher);

        // given
        voucherService.createVoucher(new VoucherReqDto(FIXED_AMOUNT, 10L));

        // then
        verify(voucherRepository).create(any());
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void testGetVouchers() {
        when(voucherRepository.findAll()).thenReturn(new ArrayList<Voucher>());

        voucherService.getVouchers();

        verify(voucherRepository).findAll();
    }

    @Test
    @DisplayName("바우처 수정 테스트")
    void testUpdateVoucher() {
        UUID voucherId = UUID.randomUUID();
        Voucher retrievedVoucher = new FixedAmountVoucher(voucherId, 10, LocalDateTime.now());

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(retrievedVoucher));

        voucherService.updateVoucher(voucherId, new VoucherReqDto(PERCENTAGE_DISCOUNT, 10));

        verify(voucherRepository).update(any());
    }

    @Test
    @DisplayName("바우처 수정 예외 테스트")
    void testUpdateVoucherException() {
        try{
            UUID voucherId = UUID.randomUUID();
            Voucher retrievedVoucher = new FixedAmountVoucher(voucherId, 10, LocalDateTime.now());
            when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(retrievedVoucher));

            voucherService.updateVoucher(voucherId, new VoucherReqDto(PERCENTAGE_DISCOUNT, 10));
        }catch (VoucherNotFoundException e) {
            verify(voucherRepository, never()).update(any());
        }
    }

    @Test
    @DisplayName("바우처 삭제 테스트")
    void testDeleteVoucher() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 10L, LocalDateTime.now());

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));

        voucherService.deleteVoucher(voucherId);

        verify(voucherRepository).delete(voucher);
    }

    @Test
    @DisplayName("바우처 삭제 예외 테스트")
    void testDeleteVoucherException() {
        try {
            UUID voucherId = UUID.randomUUID();
            when(voucherRepository.findById(voucherId)).thenThrow(new VoucherNotFoundException(voucherId));

            voucherService.deleteVoucher(voucherId);
        }catch (VoucherNotFoundException e) {
            verify(voucherRepository, never()).delete(any());
        }
    }
}