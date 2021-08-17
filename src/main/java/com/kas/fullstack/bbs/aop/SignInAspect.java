package com.kas.fullstack.bbs.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//SignInAspect에 포인트컷과 어드바이스를 정의 할 것임
@Component
@Aspect
public class SignInAspect {

//	AOP 구현
//	1. 어느 메소드가(컨트롤러) 실행할 때 ... 위치 지정:Pointcut
//	2. Pointcut으로 지정된 메소드가 실행하기 전/후에 어떤 코드가 살행될것인가를 지정해줘야 함:Advuce 
	private static final Logger logger = LoggerFactory.getLogger(SignInAspect.class);
	
	@Pointcut("execution(* com.kas.fullstack.bbs.controler.BBSController.writeForm(..))")
	public void signIn() {
		logger.info("SignInAspect의 writeForm 포인트컷 동작");
	}
	
	@Pointcut("execution(* com.kas.fullstack.bbs.controler.BBSController.write(..))")
	public void checkMethodTime() {
		logger.info("SignInAspect의 write 포인트컷 동작");
	}

	@Around("signIn()")
	public Object signInCheck(ProceedingJoinPoint pjt) throws Throwable{
		HttpSession session = null;
		
		for(Object obj : pjt.getArgs()) {
			if (obj instanceof HttpSession){
				session = (HttpSession)obj;
			}
		}
		
		if(session.getAttribute("id")==null) {
			return "login";
		}
		
		Object result = pjt.proceed(); //Controller의 메소드 실행
		
		
		//Controller의 메소드 실행되고 난 이후에 실행될 코드
		return result;
	}
	
	@Around("checkMethodTime()")
	public Object methodTime(ProceedingJoinPoint pjt) throws Throwable{
		long startTime = System.currentTimeMillis();
		
		Object result = pjt.proceed(); //Controller의 메소드 실행
		
		long methodExecutionTime =System.currentTimeMillis()-startTime;
		
		logger.info("메소드 동작 시간은 : "+methodExecutionTime);
		
		//Controller의 메소드 실행되고 난 이후에 실행될 코드
		return result;
	}


}
