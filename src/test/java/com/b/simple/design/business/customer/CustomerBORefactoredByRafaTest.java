package com.b.simple.design.business.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.b.simple.design.business.exception.DifferentCurrenciesException;
import com.b.simple.design.model.customer.Amount;
import com.b.simple.design.model.customer.AmountImpl;
import com.b.simple.design.model.customer.Currency;
import com.b.simple.design.model.customer.Product;
import com.b.simple.design.model.customer.ProductImpl;
import com.b.simple.design.model.customer.ProductType;

public class CustomerBORefactoredByRafaTest {

	private CustomerBO customerBO = new CustomerBOImpl();

	@Test
	public void testCustomerProductSum_TwoProductsSameCurrencies() throws DifferentCurrenciesException {

		Amount[] amounts = { new AmountImpl(new BigDecimal("5.0"), Currency.EURO),
				new AmountImpl(new BigDecimal("6.0"), Currency.EURO) };

		Amount expected = new AmountImpl(new BigDecimal("11.0"), Currency.EURO);

		List<Product> products = createProducstWithAmounts(amounts);

		Amount actual = customerBO.getCustomerProductsSum(products);

		assertAmount(expected, actual);
	}

	@Test
	public void testCustomerProductSum_TwoProductsDifferentCurrencies() {

		Amount[] amounts = { new AmountImpl(new BigDecimal("5.0"), Currency.INDIAN_RUPEE),
				new AmountImpl(new BigDecimal("6.0"), Currency.EURO) };

		List<Product> products = createProducstWithAmounts(amounts);

		Assertions.assertThrows(DifferentCurrenciesException.class, () -> {
			customerBO.getCustomerProductsSum(products);
		});
	}

	@Test
	public void testCustomerProductEmptyProduct() throws DifferentCurrenciesException {

		AmountImpl expected = new AmountImpl(BigDecimal.ZERO, Currency.EURO);

		Amount actual = customerBO.getCustomerProductsSum(new ArrayList<Product>());

		assertAmount(expected, actual);
	}

	private void assertAmount(Amount expected, Amount temp) {
		assertEquals(expected.getCurrency(), temp.getCurrency());
		assertEquals(expected.getValue(), temp.getValue());
	}

	private List<Product> createProducstWithAmounts(Amount[] amounts) {

		return Arrays.stream(amounts)
				.map(amount -> new ProductImpl(100, "Product 15", ProductType.BANK_GUARANTEE, amount))
				.collect(Collectors.toList());

	}

}