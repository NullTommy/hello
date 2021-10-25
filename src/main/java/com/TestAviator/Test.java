package com.TestAviator;

import com.MyUtils.StringUtils;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import lombok.SneakyThrows;

import java.util.Map;

public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("-----------1、单语句表达式---------------");
        Expression script = AviatorEvaluator.getInstance().compile("println('Hello, AviatorScript!');");
        script.execute();
        script = AviatorEvaluator.getInstance().compile("println(1+4)");
        script.execute();

        System.out.println("-------------2、多语句表达式-------------");
        Expression script2 = AviatorEvaluator.getInstance().compile("println('Hello, AviatorScript!');println(1+4);");
        Object result2 = script2.execute();
        System.out.println("result2:" + result2);

        System.out.println("-------------3、返回结果与Return-------------");
        Expression script3 = AviatorEvaluator.getInstance().compile("println('Hello, AviatorScript!');println(1+4);return '返回结果';");
        Object result3 = script3.execute();
        System.out.println("result3:" + result3);
        Expression script4 = AviatorEvaluator.getInstance().compile("println('Hello, AviatorScript!');println(1+4);'返回结果,无return'");
        Object result4 = script4.execute();
        System.out.println("result4:" + result4);

        System.out.println("-------------4、传入变量,以及return-------------");
        Expression script5 = AviatorEvaluator.getInstance().compile("a+b");
        Object result5 = script5.execute(script5.newEnv("a", 100, "b", 45));
        System.out.println("result5:" + result5);
        Expression script6 = AviatorEvaluator.getInstance().compile("return a+b;");
        Object result6 = script6.execute(script6.newEnv("a", 100, "b", 40));
        System.out.println("result6:" + result6);
        Expression script7 = AviatorEvaluator.getInstance().compile("return a+b;");
        Object result7 = script7.execute(script7.newEnv("a", "100", "b", 40));
        System.out.println("result7:" + result7);

        System.out.println("-------------5、自定义函数-------------");
        AviatorEvaluator.addFunction(new AddFunction()); //注册函数
        Expression script8 = AviatorEvaluator.getInstance().compile("return addPhc(2,5);");
        Object result8 = script8.execute();
        System.out.println("result8:" + result8);

        System.out.println("-------------5、导入Java静态类-------------");
        AviatorEvaluator.addStaticFunctions("str", StringUtils.class);
        Expression script9 = AviatorEvaluator.getInstance().compile("return str.getLength('phc');");
        Object result9 = script9.execute();
        System.out.println("result9:" + result9);
        script9 = AviatorEvaluator.getInstance().compile("return str.isNull('phc');");
        result9 = script9.execute();
        System.out.println("result9:" + result9);
        script9 = AviatorEvaluator.getInstance().compile("return str.getLength(val);");
        result9 = script9.execute(script9.newEnv("val", "panghaichen"));
        System.out.println("result9:" + result9);
    }

    static class AddFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env,
                                  AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorDouble(left.doubleValue() + right.doubleValue());
        }

        @Override
        public String getName() {
            return "addPhc";
        }
    }

}
