package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println("들어온거확인");
				
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prodNo 부분"+prodNo);
		Product product = new Product();
		product.setProdNo(prodNo);
		System.out.println("product 부분"+product);
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
				
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		
		
		HttpSession session = request.getSession();
		System.out.println("리퀘스트세션"+request.getSession());
		//======================== 수정 다시보기 =============================
		//세션에 겟어트리뷰투 프로덕트가 안들어옴.
		//세션에 불러올수있는 값이 없어서 여기들어오기전 이전단계인 업데이트 프로덕트뷰 액션에가서 세션을 셋팅해줌.
		//HttpSession Session = request.getSession(true);
		//Session.setAttribute("product", product);
		
		System.out.println("다음꺼"+session.getAttribute("product"));
		System.out.println("왜널이야"+((Product)session.getAttribute("product")).getProdNo());
		
		int sessionId = ((Product)session.getAttribute("product")).getProdNo();
		
		System.out.println("sessionId"+sessionId);
		System.out.println("product"+product);
		if( sessionId == prodNo){
		session.setAttribute("product", product);
		}
		
		return "forward:/getProduct.do?prodNo="+prodNo+"&menu=manage";
		 
		
	}
}