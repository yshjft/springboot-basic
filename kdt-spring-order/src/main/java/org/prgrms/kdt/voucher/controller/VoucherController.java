package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.io.Console;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherController {
    VoucherService voucherService;

    private final Console console = new Console();

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
        // 초기 문구 출력
        console.printInitText();
    }

    public void run() {
        // 프로그램 시작
        while (true) {
            String command = console.inputCommand("명령어를 입력하세요(create/list/exit) : ");
            switch (command) {
                // voucher 생성
                case "create" -> {
                    // voucher 타입 선택
                    String inputType = console.inputCommand("바우처 종류를 고르세요(fixed/percent) : ");
                    VoucherType voucherType = VoucherType.convert(inputType);
                    switch (voucherType){
                        case FIXED, PERCENT -> {
                            long value = Long.parseLong(console.inputCommand("할인가격or할인율을 입력하세요 : "));
                            voucherService.createVoucher(UUID.randomUUID(), voucherType, value);
                            console.printSuccess();
                        }
                        case UNDEFINED -> {
                            console.printCommandError(inputType);
                        }
                    }
                }
                // voucher 레포 리스트 출력
                case "list" -> {
                    console.printVoucherList(voucherService.getVoucherList());
                }
                // 프로그램 종료
                case "exit" -> {
                    console.printExitText();
                    return;
                }
                // 커맨드 오류시 메시지
                default -> console.printCommandError(command);
            }
        }
    }

}