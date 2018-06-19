package Result;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Service�ķ��ض���
 * User: Administrator
 * Date: 2010-4-26
 * Time: 16:59:44
 */
public class Result  {
    /**
     * �Ƿ�ɹ�
     */
    private boolean success;

    /**
     * service���صĶ���
     */
    private Map<String,Object> result = new HashMap<String,Object>();

    /**
     * Ĭ�ϵ�key
     */
    public static final String DEFAULT_MODEL_KEY = "value";

    /**
     * ��ǰ��key
     */
    private String modelKey =DEFAULT_MODEL_KEY;

    /**
     * ������
     */
    private String resultCode;
    private String[] resultCodeParams;

    /**
     * ���Ƿ�ɹ��Ĺ��췽��
     * @param success
     */
    public Result(boolean success) {
        this.success = success;
    }

    /**
     * Ĭ�Ϲ��췽��
     */
    public Result() {
    }

    /**
     * ����һ�����ؽ��
     * @param obj
     * @return
     */
    public Object addDefaultModel(Object obj) {
        return result.put(DEFAULT_MODEL_KEY,obj);        
    }

    /**
     * ����һ����key�ķ��ؽ��
     * @param key
     * @param obj
     * @return
     */
    public Object addDefaultModel(String key,Object obj) {
        modelKey=key;
        return result.put(key,obj);
    }

    /**
     * ȡ�����е�key
     * @return
     */
    public Set<String> keySet() {
        return result.keySet();
    }

    /**
     * ȡ������map����
     * @return
     */
    public Map<String,Object> getMap() {
        return result;
    }
    /**
     * ȡ��Ĭ�ϵ�ֵ
     * @return
     */
    public Object get() {
        return result.get(modelKey);
    }

    /**
     * ȡ��ֵ
     * @param key
     * @return
     */
    public Object get(String key) {
        return result.get(key);
    }

    /**
     * ȡ��ֵ����
     * @return
     */
    public Collection values() {
        return result.values();
    }
 
    /**
     * �����Ƿ�ɹ�
     * @return
     */
    public boolean getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * ���÷����Ƿ�ɹ�
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultCode(String resultCode, String... args) {
        this.resultCode = resultCode;
        this.resultCodeParams=args;
    }

    public String[] getResultCodeParams() {
        return resultCodeParams;
    }

    public void setResultCodeParams(String[] resultCodeParams) {
        this.resultCodeParams = resultCodeParams;
    }
    
}
