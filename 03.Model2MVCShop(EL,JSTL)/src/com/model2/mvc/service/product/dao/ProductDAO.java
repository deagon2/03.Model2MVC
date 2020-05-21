package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.*;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();
					//���� ����������
		String sql = "INSERT "
						+ "INTO PRODUCT "
							+ "VALUES(seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		
		stmt.executeUpdate();
		con.close();
	}
	
	 public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
					//���� ������ ����
		String sql = "SELECT * "
						+ "FROM product "
							+ "WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			}
			
		rs.close();
		stmt.close();
		con.close();

		return product;
	}
	
	
	
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		
	
		/* ================================������ ================================
		  if (searchVO.getSearchCondition() != null) {
										//if���� ��ġ������ ���ڿ��̾ƴ϶�� !��ȿ��üũ 
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("2")) {
				sql += " WHERE price='" + searchVO.getSearchKeyword()
						+ "'";
			}
		} ========================================================================
		 */ 
		
		//map ���� ������
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		//���� ������ ���� 
		String sql = "SELECT * "
					+ "FROM PRODUCT";
		if (search.getSearchCondition() != null) {
										//if���� ��ġ������ ���ڿ��̾ƴ϶�� !��ȿ��üũ 
			if (search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE prod_no='" + search.getSearchKeyword()+ "'";
			} else if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_name='" + search.getSearchKeyword()+ "'";
			} else if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE price='" + search.getSearchKeyword()+ "'";
			}
		}
		sql += " order by prod_no";
		//sql ����� üũ
		System.out.println("SQL check ::"+ sql);
		
		/*
		============================������===================================
		PreparedStatement stmt = 
			con.prepareStatement(	sql,    
											ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_UPDATABLE);
 		executeQuery(String sql) : SELECT���� ������ �� ���(ResultSet ��ü ��ȯ)	
		ResultSet rs = stmt.executeQuery();
		=====================================================================
		*/
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProdcutDAO :: totalCount  :: " + totalCount);
				
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ��� (�ؿ� make CurrentPage ���� ����)
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("SearchVO ������ִ°� :" + search);
		
		/* ================================������ ===========================
		 * HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setFileName(rs.getString("image_file"));
				vo.setManuDate(rs.getString("manufacture_day"));
				vo.setPrice(Integer.parseInt(rs.getString("price")));
				vo.setProdDetail(rs.getString("prod_detail"));
				vo.setProdName(rs.getString("prod_name"));
				vo.setProdNo(Integer.parseInt(rs.getString("prod_no")));
				vo.setRegDate(rs.getDate("reg_date"));

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
		// close(): Statement ��ü�� ��ȯ �� �� ���δ�.
		con.close();
			
		return map;
	}
		 */
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setFileName(rs.getString("image_file"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(Integer.parseInt(rs.getString("price")));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdNo(Integer.parseInt(rs.getString("prod_no")));
			product.setRegDate(rs.getDate("reg_date"));
			list.add(product);
		}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public void updateProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product "
				+ "set prod_name=?,prod_detail=?,manufacture_day=?,price=?,image_file=? "
				+ "WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getProdNo());
		
		System.out.println("DAO="+product);
	
	// executeUpdate(String sql) : ����, ����, ������ ���õ� SQL�� ���࿡ ���δ�.
		stmt.executeUpdate();
		con.close();
	}
	
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		
		// �Խ��� currentPage Row ��  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("ProdcutDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
	}