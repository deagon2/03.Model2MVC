package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request,
								HttpServletResponse response) throws Exception {

		Search search = new Search();
		
		int currentPage=1;


		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 			
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		// Business logic 수행
		ProductService productService = new ProductServiceImpl();
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage	= 						// ↓ 페이지의 객체		
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
			System.out.println("ListUserAction ::"+resultPage);
		
		// Model 과 View 연결
							//객체이름  , 객체의 벨류값
			request.setAttribute("list", map.get("list"));
			request.setAttribute("resultPage", resultPage);
			request.setAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}