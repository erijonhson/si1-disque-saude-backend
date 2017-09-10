package com.ufcg.si1.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação que identifica os recursos que precisam de autenticação para serem
 * acessados.
 * 
 * https://github.com/ericbreno/projeto-si1-backend/blob/master/src/main/java/ufcg/si/interceptor/LoginRequired.java
 * @author Eric
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}
