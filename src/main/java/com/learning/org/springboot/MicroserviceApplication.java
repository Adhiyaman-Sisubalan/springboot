package com.learning.org.springboot;

import kotlin.script.experimental.jsr223.KotlinJsr223DefaultScriptEngineFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileReader;


@SpringBootApplication
public class MicroserviceApplication implements CommandLineRunner {

	@Autowired
	private ConfigurableApplicationContext configurableApplicationContext;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var engine = new KotlinJsr223DefaultScriptEngineFactory().getScriptEngine();
		var file = new File("./helloworld.kts");
		var obj = engine.eval(new FileReader(file));
		configurableApplicationContext.getBeanFactory().registerSingleton("helloWorld", obj);

		var instanceBean = configurableApplicationContext.getBean("helloWorld");
		var invokedValue = instanceBean.getClass().getMethod("printHelloWorld").invoke(instanceBean);
		System.out.println(invokedValue);

		var list =  instanceBean.getClass().getMethod("getList").invoke(instanceBean);
		for (var str : (String[])list){
			System.out.println(str);
		}

	}
}
