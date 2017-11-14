/**
 * 
 */
package com.cai.service;

import java.util.List;

import javax.jws.WebService;

import com.cai.domain.Customer;

/**
 * @author crc
 *	@date 2017年11月14日 下午8:04:35
 */
@WebService
public interface ICustomerService {
	public List<Customer> findAll();
	public List<Customer> findListNotAssociation();
	public List<Customer> findListAssociation(String decidedzoneId);
}
