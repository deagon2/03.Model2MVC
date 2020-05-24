package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		
//		쿠키란?
//		쿠키는 서버가 클라이언트에 저장하는 정보로서, 클라이언트와 연결이 끊어져도 클라이언트에 저장된 정보가 
//		유지되어 서버에 재 방문할 때 요청정보의 헤더 안에 포함되어 서버로 전달됩니다.
//		
//		*언제 사용하는가?
//				이전에 방문한 적이 있는 웹서버에 다시 방문했을 때 몇 번째 방문인지
//				비로그인자가 쇼핑몰에서 주문할 때까지 장바구니에 선택한 상품 정보들을 유지
//				포탈 사이트에서 클라이언트가 특별히 관심 있어하는 항목에 대한 정보 유지
//				자동 로그인을 허용할 때
		
		//URI에서 prodNo를 가져와서 쿠키를 쓸 준비를함.
		
//		쿠키 추출
//		클라이언트로 전송된 쿠키를 서버쪽에서 읽어 들이려면 HttpServletRequest 객체의 getCookies() 메소드를 이용한다.
//
//		Cookie[] list = req.getCookies();
//		쿠키의 이름을 추출할 때는 Cookie 객체의 getName( ) 메소드를 사용한다.
//
//		for(int i=0; list!=null && i < list.length; i++ ){
//		    out.println(list[i].getName() + "<br>");
//		}
//		쿠키의 값을 추출 할때는 Cookie 객체의 getValue( ) 메소드를 사용한다.
//
//		for(int i=0; list!=null && i < list.length; i++ ){
//		    out.println(list[i].getValue() + "<br>");
//		}	
//		
		//URI를 통해 넘어온 쿠키 추출.
		Cookie[] cookies = request.getCookies(); 
		//쿠키의 값이 비어있지 않고 쿠키의길이가 0보다 작으면 트루
		if(cookies!=null && cookies.length>0) {
			//쿠키를 쿠키의 길이만큼
			for(int i=0;i<cookies.length;i++) {	
				//쿠키어레이에 에 넣음
				Cookie cookie = cookies[i];
				//쿠키의 이름이 히스토리와 같다면 
			if(cookie.getName().equals("history")) {
				//					쿠키이름                         쿠키값
				cookie.setValue(cookie.getValue()+","+prodNo);
				//한시간동안 생성함.
				cookie.setMaxAge(60*60); 
				//쿠키를 리스폰스에 태워서 보냄.
				response.addCookie(cookie);
			}else{
				//없으면 이름이 히스토리인  uri에서 가져온 prodNo를 쿠키로 생성
			String cookies1 = request.getParameter("prodNo");
			cookie = new Cookie("history",cookies1);
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			}
		  }
		}
			
		
		
		
		
		ProductService service = new ProductServiceImpl();
		Product vo = service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
			return "forward:/product/getProduct.jsp";
		
	
	}
}