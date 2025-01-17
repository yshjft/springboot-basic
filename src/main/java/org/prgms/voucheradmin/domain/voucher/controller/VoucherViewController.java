package org.prgms.voucheradmin.domain.voucher.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoucherViewController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherViewController.class);
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        logger.error(ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "views/error";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(VoucherReqDto voucherReqDto) {
        voucherService.createVoucher(voucherReqDto);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", allVouchers);
        return "views/voucher/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage(Model model)  {
        model.addAttribute("voucherTypes", VoucherType.values());
        return "views/voucher/new-voucher";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String viewVoucherPage(@PathVariable UUID voucherId, Model model)  {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "views/voucher/voucher";
    }

    @GetMapping("/vouchers/{voucherId}/update")
    public String viewVoucherUpdatePage(@PathVariable UUID voucherId, Model model)  {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        model.addAttribute("voucherTypes", VoucherType.values());
        return "views/voucher/update-voucher";
    }

    @PostMapping("/vouchers/update/{voucherId}")
    public String updateVoucher(@PathVariable UUID voucherId, VoucherReqDto voucherReqDto) {
        voucherService.updateVoucher(voucherId, voucherReqDto);
        return "redirect:/vouchers/"+voucherId;
    }

    @PostMapping("/vouchers/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId)  {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
