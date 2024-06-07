package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;
import vn.webapp.backend.auction.model.Bank;
import vn.webapp.backend.auction.service.BankService;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class BankServiceTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private BankService bankService;

    @Test
    public void testGetAllBankAndReturnsWell() {
        List<Bank> banks = bankService.getAll();
        assertNotNull(banks);
        assertFalse(banks.isEmpty());
    }

    @Test
    public void testGetBankByIdAndReturnRightBank() {
        Bank bank = new Bank();
        bank.setId(1);
        Bank foundBank = bankService.getById(bank.getId());
        assertNotNull(foundBank);
        assertEquals(foundBank.getBankName(), "Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam");
    }
}