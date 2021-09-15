package org.prgrms.kdt.engine.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@Transactional
class NamedJdbcVoucherRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.engine"})
    static class Config {

        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/order_mgmt")
                .username("root")
                .password("1234")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;
    @Autowired
    NamedJdbcVoucherRepository repository;

    Voucher voucher = VoucherType.FIXED.createVoucher(10);

    @BeforeEach
    void setup() {
        repository.insert(voucher);
    }

    @Test
    @DisplayName("바우처를 추가할 수 있다")
    void testInsert() {
        var newVoucher = VoucherType.FIXED.createVoucher(10);
        repository.insert(newVoucher);

        var maybeNewVoucher = repository.findById(newVoucher.getVoucherId());
        assertThat(maybeNewVoucher.isEmpty(), is(false));
        assertThat(maybeNewVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("ID로 바우처를 조회할 수 있다")
    void testFindById() {
        var maybeNewVoucher = repository.findById(voucher.getVoucherId());
        assertThat(maybeNewVoucher.isEmpty(), is(false));
        assertThat(maybeNewVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("모든 바우처를 반환할 수 있다")
    void testGetAll() {
        var vouchers = repository.getAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.get().size(), is(1));
    }

}