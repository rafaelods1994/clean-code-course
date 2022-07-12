package com.b.simple.design.business.customer;

import java.math.BigDecimal;
import java.util.List;

import com.b.simple.design.business.exception.DifferentCurrenciesException;
import com.b.simple.design.model.customer.Amount;
import com.b.simple.design.model.customer.AmountImpl;
import com.b.simple.design.model.customer.Currency;
import com.b.simple.design.model.customer.Product;

public class CustomerBOImpl implements CustomerBO {

	@Override
	public Amount getCustomerProductsSum(List<Product> products) throws DifferentCurrenciesException {

		if (products.size() == 0)
			return createNewProduct(BigDecimal.ZERO, Currency.EURO);

		Currency firstProductCurrency = getFirstProductCurrency(products);
		if (!doAllProductsHaveSameCurrency(products, firstProductCurrency)) {
			throw new DifferentCurrenciesException();
		}

		BigDecimal sumOfProducts = calculateSumOfProducts(products);
		return createNewProduct(sumOfProducts, firstProductCurrency);
	}

	private AmountImpl createNewProduct(BigDecimal sumOfProducts, Currency firstProductCurrency) {
		return new AmountImpl(sumOfProducts, firstProductCurrency);
	}

	private BigDecimal calculateSumOfProducts(List<Product> products) {

		return products.stream()
				.map(product -> product.getAmount().getValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Currency getFirstProductCurrency(List<Product> products) {
		return products.get(0).getAmount().getCurrency();
	}

	public boolean doAllProductsHaveSameCurrency(List<Product> products, Currency firstProductCurrency)
			throws DifferentCurrenciesException {
		return products.stream().map(product -> product.getAmount().getCurrency())
				.allMatch(currency -> currency.equals(firstProductCurrency));

	}

}