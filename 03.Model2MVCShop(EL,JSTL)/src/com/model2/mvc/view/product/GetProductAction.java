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
		
		
//		��Ű��?
//		��Ű�� ������ Ŭ���̾�Ʈ�� �����ϴ� �����μ�, Ŭ���̾�Ʈ�� ������ �������� Ŭ���̾�Ʈ�� ����� ������ 
//		�����Ǿ� ������ �� �湮�� �� ��û������ ��� �ȿ� ���ԵǾ� ������ ���޵˴ϴ�.
//		
//		*���� ����ϴ°�?
//				������ �湮�� ���� �ִ� �������� �ٽ� �湮���� �� �� ��° �湮����
//				��α����ڰ� ���θ����� �ֹ��� ������ ��ٱ��Ͽ� ������ ��ǰ �������� ����
//				��Ż ����Ʈ���� Ŭ���̾�Ʈ�� Ư���� ���� �־��ϴ� �׸� ���� ���� ����
//				�ڵ� �α����� ����� ��
		
		//URI���� prodNo�� �����ͼ� ��Ű�� �� �غ���.
		
//		��Ű ����
//		Ŭ���̾�Ʈ�� ���۵� ��Ű�� �����ʿ��� �о� ���̷��� HttpServletRequest ��ü�� getCookies() �޼ҵ带 �̿��Ѵ�.
//
//		Cookie[] list = req.getCookies();
//		��Ű�� �̸��� ������ ���� Cookie ��ü�� getName( ) �޼ҵ带 ����Ѵ�.
//
//		for(int i=0; list!=null && i < list.length; i++ ){
//		    out.println(list[i].getName() + "<br>");
//		}
//		��Ű�� ���� ���� �Ҷ��� Cookie ��ü�� getValue( ) �޼ҵ带 ����Ѵ�.
//
//		for(int i=0; list!=null && i < list.length; i++ ){
//		    out.println(list[i].getValue() + "<br>");
//		}	
//		
		//URI�� ���� �Ѿ�� ��Ű ����.
		Cookie[] cookies = request.getCookies(); 
		//��Ű�� ���� ������� �ʰ� ��Ű�Ǳ��̰� 0���� ������ Ʈ��
		if(cookies!=null && cookies.length>0) {
			//��Ű�� ��Ű�� ���̸�ŭ
			for(int i=0;i<cookies.length;i++) {	
				//��Ű��̿� �� ����
				Cookie cookie = cookies[i];
				//��Ű�� �̸��� �����丮�� ���ٸ� 
			if(cookie.getName().equals("history")) {
				//					��Ű�̸�                         ��Ű��
				cookie.setValue(cookie.getValue()+","+prodNo);
				//�ѽð����� ������.
				cookie.setMaxAge(60*60); 
				//��Ű�� ���������� �¿��� ����.
				response.addCookie(cookie);
			}else{
				//������ �̸��� �����丮��  uri���� ������ prodNo�� ��Ű�� ����
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