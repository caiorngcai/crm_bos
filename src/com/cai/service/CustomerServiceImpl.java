/**
 * 
 */
package com.cai.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.cai.domain.Customer;

/**
 * @author crc
 *	@date 2017年11月14日 下午8:05:33
 */
@Transactional
public class CustomerServiceImpl implements ICustomerService{
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/* 
	 * 查询所有客户的方法，作为webservice方法
	 */
	public List<Customer> findAll() {
		String sql="select * from t_customer";
		List<Customer> list=jdbcTemplate.query(sql,new RowMapper<Customer>() {

			@Override
			public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String station = rs.getString("station");
				String telephone = rs.getString("telephone");
				String address = rs.getString("address");
				String decidedzone_id = rs.getString("decidedzone_id");
				return new Customer(id, name, station, telephone, address, decidedzone_id);
	
			}
		});
		return list;
	}


	/* 
	 * 查询未与定区关联的客户方法
	 */
	public List<Customer> findListNotAssociation() {
		String sql="select * from t_customer where decidedzone_id is null";
		List<Customer> list=jdbcTemplate.query(sql,new RowMapper<Customer>() {
			@Override
			public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
				int id = rs.getInt("id");//根据字段名称从结果集中获取对应的值
				String name = rs.getString("name");
				String station = rs.getString("station");
				String telephone = rs.getString("telephone");
				String address = rs.getString("address");
				String decidedzone_id = rs.getString("decidedzone_id");
				return new Customer(id, name, station, telephone, address, decidedzone_id);
			}
		});
		return list;
	}


	/* 
	 *查询与定区关联的客户 
	 */
	public List<Customer> findListAssociation(String decidedzoneId) {
		String sql="select * from t_customer where decidedzone_id=?";
		List<Customer> list=jdbcTemplate.query(sql,new RowMapper<Customer>() {

			@Override
			public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
				int id = rs.getInt("id");//根据字段名称从结果集中获取对应的值
				String name = rs.getString("name");
				String station = rs.getString("station");
				String telephone = rs.getString("telephone");
				String address = rs.getString("address");
				String decidedzone_id = rs.getString("decidedzone_id");
				return new Customer(id, name, station, telephone, address, decidedzone_id);

			}
		},decidedzoneId);
		return list;
	}
	/* 
	 * 定区关联客户的webservice方法
	 */
	public void assignCustomerToDecidedzone(String decidedzoneId, Integer[] customerIds) {
		String sql="update t_customer set decidedzone_id=null where decidedzone_id=?";
		//清空所有，重新来过
		jdbcTemplate.update(sql, decidedzoneId);
		sql="update t_customer set decidedzone_id= ? where id = ?";
		for (Integer id : customerIds) {
			jdbcTemplate.update(sql,decidedzoneId,id);
		}
	}

}
