package com.shouyingbao.pbs.core.common;
public class JsonResultNew<T> {
	private T        result;
    private MetaData meta;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public static <T> JsonResultNew<T> returnResult(T t,String msg,Integer errno){
    	 MetaData meta = new MetaData(errno, msg);
    	 JsonResultNew<T> result = new JsonResultNew<T>();
    	 result.setMeta(meta);
    	 result.setResult(t);
    	 return result;
    }
    

    public static class MetaData {

        private Integer errno;
        private String  msg;

        public MetaData(Integer errno, String msg) {
            this.errno = errno;
            this.msg = msg;
           
        }

        public Integer getErrno() {
            return errno;
        }

        public void setErrno(Integer errno) {
            this.errno = errno;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


    }
}
